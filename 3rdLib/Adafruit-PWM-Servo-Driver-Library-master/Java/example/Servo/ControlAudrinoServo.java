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


public class ControlAudrinoServo
{
	// Initialise the PWM device using the default address 0x40 (first ServoDriver)
	private JavaPWM m_pwm = new JavaPWM(0x40, true);
	// you can also call it with a different address you want
	// Adafruit_PWMServoDriver pwm = Adafruit_PWMServoDriver(0x41);
	
	// Depending on your servo make, the pulse width min and max may vary, you
	// want these to be as small/large as possible without hitting the hard stop
	// for max range. You'll have to tweak them as necessary to match the servos you
	// have!
	private int m_servoMin = 200; // Min pulse length out of 4096
	private int m_servoMax = 400; // Max pulse length out of 4096

	public ControlAudrinoServo()
	{
	}

	/**
	 * you can use this function if you'd like to set the pulse length in seconds
	 *  e.g. setServoPulse(0, 0.001) is a ~1 millisecond pulse width. its not precise!
	 *  
	 * @param pulse
	 * @param channel
	 * @throws Exception
	 */
	public void setServoPulse(int pulse, int channel) throws Exception
	{
		int pulseLength;

		pulseLength = 1000000; // 1,000,000 us per second
		pulseLength /= 60; // 60 Hz
		System.out.println(pulseLength + " us per period");

		pulseLength /= 4096; // 12 bits of resolution
		System.out.println(pulseLength + " us per bit");

		pulse *= 1000;
		pulse /= pulseLength;

		m_pwm.setPWM(channel, 0, pulse);
	}

	public void testJavaServoDriver() throws Exception
	{
		// Set frequency to 60 Hz (every X microseconds)
		m_pwm.setPWMFreq(60);

		int i = 10;
		while (i-- > 0)
		{
			// Send PWQ-signal to servo on channel 0, start and end of signal
			// 5V
			m_pwm.setPWM(0, 0, m_servoMin);
			Thread.sleep(1000);

			// Send PWQ-signal to servo on channel 0, start and end of signal
			// 5V
			m_pwm.setPWM(0, 0, m_servoMax);
			Thread.sleep(1000);

			// Send PWQ-signal to servo on channel 0, start and end of signal
			m_pwm.setPWM(0, 0, (m_servoMin + m_servoMax) / 2);
			Thread.sleep(1000);
		}
	}

	public static void main(String[] args) throws Exception
	{
		new ControlAudrinoServo().testJavaServoDriver();
	}
}