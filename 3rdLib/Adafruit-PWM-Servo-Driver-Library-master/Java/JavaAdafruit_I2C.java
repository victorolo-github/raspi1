/***************************************************
This I2C library for an Adafruit PCA9685 using PI4J.

Original/main author(s):
Miguel Garvia
http://unpocodejava.wordpress.com/2013/08/19/control-de-servos-con-java-y-raspberry-pi-mediante-i2c/

****************************************************/

import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CFactory;

public class JavaAdafruit_I2C
{
	private static JavaAdafruit_I2C instance;

	protected I2CBus m_bus;
	private int m_address;
	private boolean m_debug;

	public static JavaAdafruit_I2C getInstance(int address, boolean debug)
	{
		if (instance == null)
		{
			try
			{
				instance = new JavaAdafruit_I2C(address, debug);
				instance.init();
			} catch (Exception ex)
			{
				ex.printStackTrace();
			}
		}
		return instance;
	}

	public JavaAdafruit_I2C(int address, boolean debug)
	{
		this.m_address = address;
		this.m_debug = debug;
	}

	public void init() throws Exception
	{
		m_bus = I2CFactory.getInstance(I2CBus.BUS_1);
	}

	public void write8(int reg, byte value)
	{
		// "Writes an 8-bit value to the specified register/address"
		try
		{
			this.m_bus.getDevice(m_address).write(reg, value);
			if (this.m_debug)
			{
				System.out.println("I2C: Wrote 0x" + value + "X to register 0x"
						+ (byte) (reg) + "X");
			}
		} catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}

	public int readU8(int reg)
	{
		// "Reads a signed byte from the I2C device"
		try
		{
			int result = this.m_bus.getDevice(m_address).read(reg);
			if (result > 127)
			{
				result -= 256;
			}
			if (this.m_debug)
			{
				System.out.println("I2C: Device 0x" + m_address + "X returned 0x"
						+ result + "X from reg 0x" + reg + "X");
			}
			return result;
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return -1;
	}
}