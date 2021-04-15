/***************************************************
This is a combination of the python, java and c++ librarys made
for an Adafruit PCA9685 using a servo.

Combination of:
http://unpocodejava.wordpress.com/2013/08/19/control-de-servos-con-java-y-raspberry-pi-mediante-i2c/
https://github.com/adafruit/Adafruit-PWM-Servo-Driver-Library
https://github.com/adafruit/Adafruit-Raspberry-Pi-Python-Code

Author: Pablo Santibañez Jara
Original/main author(s):
Miguel Garvia

****************************************************

This is a library for our Adafruit 16-channel PWM & Servo driver

Pick one up today in the adafruit shop!
------> http://www.adafruit.com/products/815

These displays use I2C to communicate, 2 pins are required to
interface. For Arduino UNOs, thats SCL -> Analog 5, SDA -> Analog 4

Adafruit invests time and resources providing this open source code,
please support Adafruit and open-source hardware by purchasing
products from Adafruit!

Written by Limor Fried/Ladyada for Adafruit Industries.
BSD license, all text above must be included in any redistribution
****************************************************/

public class JavaPWM
{
	// Registers/etc.
	public static int __SUBADR1 = 0x02;
	public static int __SUBADR2 = 0x03;
	public static int __SUBADR3 = 0x04;
	public static int __MODE1 = 0x00;
	public static int __PRESCALE = 0xFE;
	public static int __LED0_ON_L = 0x06;
	public static int __LED0_ON_H = 0x07;
	public static int __LED0_OFF_L = 0x08;
	public static int __LED0_OFF_H = 0x09;
	public static int __ALLLED_ON_L = 0xFA;
	public static int __ALLLED_ON_H = 0xFB;
	public static int __ALLLED_OFF_L = 0xFC;
	public static int __ALLLED_OFF_H = 0xFD;

	protected JavaAdafruit_I2C m_i2c = null;
	private int m_address;
	private boolean m_debug;

	public JavaPWM(int address, boolean debug)
	{
		this.m_address = address;
		this.m_debug = debug;
		
		this.m_i2c = JavaAdafruit_I2C.getInstance(m_address, m_debug);
		
		if (this.m_debug)
		{
			System.out.println("Reseting PCA9685");
		}
		this.m_i2c.write8(__MODE1, (byte) (0x00));
	}

	public void setPWMFreq(int freq) throws Exception
	{
		// "Sets the PWM frequency"
		float prescaleval = 25000000.0f; // 25MHz
		prescaleval /= 4096.0; // 12-bit
		prescaleval /= (float) (freq);
		prescaleval -= 1.0;
		if (this.m_debug)
		{
			System.out.println("Setting PWM frequency to " + freq + " Hz");
			System.out.println("Estimated pre-scale: " + prescaleval);
		}
		float prescale = (float) (Math.floor(prescaleval + 0.5f));
		if (this.m_debug)
		{
			System.out.println("Final pre-scale: " + prescale);
		}

		int oldmode = this.m_i2c.readU8(__MODE1);
		int newmode = (oldmode & 0x7F) | 0x10; // sleep
		this.m_i2c.write8(__MODE1, (byte) (newmode)); // go to sleep
		this.m_i2c.write8(__PRESCALE, (byte) (Math.floor(prescale)));
		this.m_i2c.write8(__MODE1, (byte) (oldmode));

		Thread.sleep(5);

		this.m_i2c.write8(__MODE1, (byte) (oldmode | 0x80));
	}

	public void setPWM(int channel, int on, int off)
	{
		// "Sets a single PWM channel"
		this.m_i2c.write8(__LED0_ON_L + 4 * channel, (byte) (on & 0xFF));
		this.m_i2c.write8(__LED0_ON_H + 4 * channel, (byte) (on >> 8));
		this.m_i2c.write8(__LED0_OFF_L + 4 * channel, (byte) (off & 0xFF));
		this.m_i2c.write8(__LED0_OFF_H + 4 * channel, (byte) (off >> 8));
	}
}