/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.virole.raspi1.propulsion;

import com.ia.config.ConfigManager;
import com.pi4j.wiringpi.SoftPwm;
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.virole.bitacora.Bitacora;
import com.virole.raspi1.mock.MockGpioFactory;
import com.virole.raspi1.mock.MockPin;
import java.util.logging.Level;

/**
 *
 * @author ulabvrl
 */
public class EngineManager {

    private static EngineManager instance = null;
    final GpioController gpio;
    GpioPinDigitalOutput pinEnableMotorIzq;
    GpioPinDigitalOutput pinEnableMotorForward;
    GpioPinDigitalOutput pinEnableMotorReverse;
//    public static final int MOTOR_IZQ_FORWARD = RaspiPin.GPIO_04.getAddress(); // GPIO 04
//    public static final int MOTOR_IZQ_REVERSE = RaspiPin.GPIO_05.getAddress(); // GPIO 05
//    public static final int MOTOR_IZQ_ENABLE = 12; // RaspiPin.GPIO_18

    public EngineManager() {
        // initialize wiringPi library
        if (!ConfigManager.getInstance().isModeSimulation()) {
            com.pi4j.wiringpi.Gpio.wiringPiSetup();
            gpio = GpioFactory.getInstance();
            // crear pines pwm por software (id pinEnableMotorIzq, min=0 ; max=100)
//            SoftPwm.softPwmCreate(MOTOR_IZQ_FORWARD, 0, 100);
//            SoftPwm.softPwmCreate(MOTOR_IZQ_REVERSE, 0, 100);
            // Este sera el pinEnableMotorIzq “enabled” del controlador para el motor 1 
            pinEnableMotorIzq = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_01);
            pinEnableMotorForward = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_04);
            pinEnableMotorReverse = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_05);
            Bitacora.getInstance().write(String.format(" RASPI1 GPIO 01 = ('%d').", RaspiPin.GPIO_01.getAddress()), Level.INFO);
            Bitacora.getInstance().write(String.format(" RASPI1 GPIO 04 = ('%d').", RaspiPin.GPIO_04.getAddress()), Level.INFO);
            Bitacora.getInstance().write(String.format(" RASPI1 GPIO 05 = ('%d').", RaspiPin.GPIO_05.getAddress()), Level.INFO);
        } else {
            gpio = MockGpioFactory.getInstance();
            pinEnableMotorIzq = gpio.provisionDigitalOutputPin(MockPin.DIGITAL_OUTPUT_PIN_GPI18, "EnableMotorIzq", PinState.HIGH);
            pinEnableMotorForward = gpio.provisionDigitalOutputPin(MockPin.DIGITAL_OUTPUT_PIN_GPI18, "pinEnableMotorForward", PinState.HIGH);
            pinEnableMotorReverse = gpio.provisionDigitalOutputPin(MockPin.DIGITAL_OUTPUT_PIN_GPI18, "pinEnableMotorReverse", PinState.HIGH);
        }

    }

    public void start() {
        // Arrancamos el motor de la izquierda
        Bitacora.getInstance().write(" RASPI1 started left engine.", Level.INFO);
        pinEnableMotorIzq.high();
    }

    public void stop() {
        // Paramos el motor de la izquierda
        Bitacora.getInstance().write(" RASPI1 stop left engine.", Level.INFO);
        pinEnableMotorIzq.low();
    }

    public void forward(int speed) {
        Bitacora.getInstance().write(" RASPI1 engine forwarding.", Level.INFO);
        if (!ConfigManager.getInstance().isModeSimulation()) {
//            SoftPwm.softPwmWrite(MOTOR_IZQ_REVERSE, 0);
//            SoftPwm.softPwmWrite(MOTOR_IZQ_FORWARD, speed);

            
            Bitacora.getInstance().write(String.format(" RASPI1 engine forwarding %d.", speed), Level.INFO);
            
            pinEnableMotorForward.high();
            pinEnableMotorReverse.low();
            
        } else {
            Bitacora.getInstance().write(String.format(" RASPI1 SIMULATION MODE engine forwarding %d.", speed), Level.INFO);
        }
    }

    public void reverse(int speed) {
        Bitacora.getInstance().write(" RASPI1 engine reversing.", Level.INFO);
        if (!ConfigManager.getInstance().isModeSimulation()) {
            
//            SoftPwm.softPwmWrite(MOTOR_IZQ_FORWARD, 0);
//            SoftPwm.softPwmWrite(MOTOR_IZQ_REVERSE, speed);
            Bitacora.getInstance().write(String.format(" RASPI1 engine reversing %d.", speed), Level.INFO);
            
            pinEnableMotorForward.low();
            pinEnableMotorReverse.high();
            
        } else {
            Bitacora.getInstance().write(String.format(" RASPI1 SIMULATION MODE engine reversing %d.", speed), Level.INFO);
        }
    }

    public static synchronized EngineManager getInstance() {
        if (instance == null) {
            instance = new EngineManager();
        }
        return instance;
    }
}
