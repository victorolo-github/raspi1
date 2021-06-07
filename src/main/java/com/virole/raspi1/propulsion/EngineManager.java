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
    GpioPinDigitalOutput pinEnableMotor1;
    GpioPinDigitalOutput pinMotor1Forward;
    GpioPinDigitalOutput pinMotor1Reverse;
    GpioPinDigitalOutput pinEnableMotor2;
    GpioPinDigitalOutput pinMotor2Forward;
    GpioPinDigitalOutput pinMotor2Reverse;
//    public static final int MOTOR_IZQ_FORWARD = RaspiPin.GPIO_04.getAddress(); // GPIO 04
//    public static final int MOTOR_IZQ_REVERSE = RaspiPin.GPIO_05.getAddress(); // GPIO 05
//    public static final int MOTOR_IZQ_ENABLE = 12; // RaspiPin.GPIO_18

    public EngineManager() {
        // initialize wiringPi library
        if (!ConfigManager.getInstance().isModeSimulation()) {
            com.pi4j.wiringpi.Gpio.wiringPiSetup();
            gpio = GpioFactory.getInstance();
            // crear pines pwm por software (id pinEnableMotor1, min=0 ; max=100)
//            SoftPwm.softPwmCreate(MOTOR_IZQ_FORWARD, 0, 100);
//            SoftPwm.softPwmCreate(MOTOR_IZQ_REVERSE, 0, 100);
            // Este sera el pinEnableMotor1 “enabled” del controlador para el motor 1 
            pinEnableMotor1 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_01);
            pinMotor1Forward = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_04);
            pinMotor1Reverse = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_05);
            pinEnableMotor2 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_06);
            pinMotor2Forward = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_16);
            pinMotor2Reverse = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_15);
            Bitacora.getInstance().write(String.format(" RASPI1 GPIO 01 = ('%d').", RaspiPin.GPIO_01.getAddress()), Level.INFO);
            Bitacora.getInstance().write(String.format(" RASPI1 GPIO 04 = ('%d').", RaspiPin.GPIO_04.getAddress()), Level.INFO);
            Bitacora.getInstance().write(String.format(" RASPI1 GPIO 05 = ('%d').", RaspiPin.GPIO_05.getAddress()), Level.INFO);
        } else {
            gpio = MockGpioFactory.getInstance();
            pinEnableMotor1 = gpio.provisionDigitalOutputPin(MockPin.DIGITAL_OUTPUT_PIN_GPI01, "EnableMotor1", PinState.HIGH);
            pinMotor1Forward = gpio.provisionDigitalOutputPin(MockPin.DIGITAL_OUTPUT_PIN_GPI04, "pinEnableMotor1Forward", PinState.HIGH);
            pinMotor1Reverse = gpio.provisionDigitalOutputPin(MockPin.DIGITAL_OUTPUT_PIN_GPI05, "pinEnableMotor1Reverse", PinState.HIGH);
            pinEnableMotor2 = gpio.provisionDigitalOutputPin(MockPin.DIGITAL_OUTPUT_PIN_GPI06, "EnableMotor2", PinState.HIGH);
            pinMotor2Forward = gpio.provisionDigitalOutputPin(MockPin.DIGITAL_OUTPUT_PIN_GPI015, "pinEnableMotor2Forward", PinState.HIGH);
            pinMotor2Reverse = gpio.provisionDigitalOutputPin(MockPin.DIGITAL_OUTPUT_PIN_GPI016, "pinEnableMotor2Reverse", PinState.HIGH);
        }

    }

    public void enable() {
        // Arrancamos el motor de la izquierda
        Bitacora.getInstance().write(" RASPI1 started left engine.", Level.INFO);
        pinEnableMotor1.high();
        pinEnableMotor2.high();
        stop();
    }

    public void disable() {
        // Paramos el motor de la izquierda
        Bitacora.getInstance().write(" RASPI1 stop left engine.", Level.INFO);
        pinEnableMotor1.low();
        pinEnableMotor2.low();
        stop();
    }

    public void forward(int speed) {
        Bitacora.getInstance().write(" RASPI1 engine forwarding.", Level.INFO);
        if (!ConfigManager.getInstance().isModeSimulation()) {
//            SoftPwm.softPwmWrite(MOTOR_IZQ_REVERSE, 0);
//            SoftPwm.softPwmWrite(MOTOR_IZQ_FORWARD, speed);

            Bitacora.getInstance().write(String.format(" RASPI1 engine forwarding %d.", speed), Level.INFO);

            pinMotor1Forward.high();
            pinMotor1Reverse.low();
            pinMotor2Forward.low();
            pinMotor2Reverse.high();

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

            pinMotor1Forward.low();
            pinMotor1Reverse.high();
            pinMotor2Forward.high();
            pinMotor2Reverse.low();

        } else {
            Bitacora.getInstance().write(String.format(" RASPI1 SIMULATION MODE engine reversing %d.", speed), Level.INFO);
        }
    }

    public void right(int speed) {
        Bitacora.getInstance().write(" RASPI1 engine right.", Level.INFO);
        if (!ConfigManager.getInstance().isModeSimulation()) {

//            SoftPwm.softPwmWrite(MOTOR_IZQ_FORWARD, 0);
//            SoftPwm.softPwmWrite(MOTOR_IZQ_REVERSE, speed);
            Bitacora.getInstance().write(String.format(" RASPI1 engine reversing %d.", speed), Level.INFO);

            pinMotor1Forward.high();
            pinMotor1Reverse.low();
            pinMotor2Forward.high();
            pinMotor2Reverse.low();

        } else {
            Bitacora.getInstance().write(String.format(" RASPI1 SIMULATION MODE engine reversing %d.", speed), Level.INFO);
        }
    }

    public void left(int speed) {
        Bitacora.getInstance().write(" RASPI1 engine left.", Level.INFO);
        if (!ConfigManager.getInstance().isModeSimulation()) {

//            SoftPwm.softPwmWrite(MOTOR_IZQ_FORWARD, 0);
//            SoftPwm.softPwmWrite(MOTOR_IZQ_REVERSE, speed);
            Bitacora.getInstance().write(String.format(" RASPI1 engine reversing %d.", speed), Level.INFO);

            pinMotor1Forward.low();
            pinMotor1Reverse.high();
            pinMotor2Forward.low();
            pinMotor2Reverse.high();

        } else {
            Bitacora.getInstance().write(String.format(" RASPI1 SIMULATION MODE engine reversing %d.", speed), Level.INFO);
        }
    }

    public void stop() {
        pinMotor1Forward.low();
        pinMotor1Reverse.low();
        pinMotor2Forward.low();
        pinMotor2Reverse.low();
    }

    public static synchronized EngineManager getInstance() {
        if (instance == null) {
            instance = new EngineManager();
        }
        return instance;
    }
}
