package com.virole.raspi1;

import com.ia.config.ConfigManager;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;
import com.virole.bitacora.Bitacora;
import com.virole.raspi1.mock.MockGpioFactory;
import com.virole.raspi1.mock.MockPin;
import com.virole.raspi1.propulsion.EngineManager;
import java.util.logging.Level;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

public class FXMLController implements Initializable {

    final GpioController gpio;
    final GpioPinDigitalOutput pin;
    private boolean isPinLow;
    private EngineManager engineManager;
    @FXML
    private Button button;
    @FXML
    private Button forwardButton;
    private Button reversedButton;
    @FXML
    private Button rightButton;
    @FXML
    private Button leftButton;
    @FXML
    private Button reverseButton;

    public FXMLController() {
        // crear controlador gpio
        if (!ConfigManager.getInstance().isModeSimulation()) {
            Bitacora.getInstance().write("Creating Controller GPIO", Level.INFO);
            this.gpio = GpioFactory.getInstance();
            Bitacora.getInstance().write(String.format("Creating PIN GPIO: '%s'", RaspiPin.GPIO_07.getName()), Level.INFO);
            this.pin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_07, "MyLED", PinState.HIGH);
            Bitacora.getInstance().write(String.format("End Creating PIN GPIO: '%s'", RaspiPin.GPIO_07.getName()), Level.INFO);
        } else {
            Bitacora.getInstance().write("Creating Controller MOCK GPIO", Level.INFO);
            this.gpio = MockGpioFactory.getInstance();
            Bitacora.getInstance().write(String.format("Creating PIN GPIO: '%s'", RaspiPin.GPIO_07.getName()), Level.INFO);
            this.pin = gpio.provisionDigitalOutputPin(MockPin.DIGITAL_OUTPUT_PIN_GPIO7, "MyLED", PinState.HIGH);

        }

        engineManager = EngineManager.getInstance();
        engineManager.enable();

        isPinLow = true;
        pin.low();
    }

    @FXML
    private Label label;

    @FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");

        if (isPinLow) {
            isPinLow = false;
            pin.high();
//            pin12.high();
//            pin16.high();
//            pin18.high();
            label.setText("Encendida 12!!!");
            Bitacora.getInstance().write(String.format("Led '%s' encendido!!!", pin.getName()), Level.INFO);

            engineManager.forward(100);

        } else {
            isPinLow = true;
            pin.low();
//            pin12.low();
//            pin16.low();
//            pin18.low();
            label.setText("Apagada 12!!!");
            Bitacora.getInstance().write(String.format("Led '%s' apagado!!!", pin.getName()), Level.INFO);

            engineManager.reverse(100);
        }
    }

//    private void pressedLeft(ActionEvent event) {
//        Bitacora.getInstance().write("RASPI1 FORWARED!!!", Level.INFO);
//        engineManager.forward(100);
//
//    }
//
//    private void realeaseLeft(ActionEvent event) {
//        Bitacora.getInstance().write("RASPI1 FORWARED!!!", Level.INFO);
//        engineManager.forward(0);
//    }
//
//    private void pressedForward(ActionEvent event) {
//        Bitacora.getInstance().write("RASPI1 FORWARED!!!", Level.INFO);
//        engineManager.forward(100);
//    }
//
//    private void releasedForward(ActionEvent event) {
//        Bitacora.getInstance().write("RASPI1 FORWARED!!!", Level.INFO);
//        engineManager.forward(0);
//    }
//
//    private void pressedRight(ActionEvent event) {
//        Bitacora.getInstance().write("RASPI1 FORWARED!!!", Level.INFO);
//        engineManager.forward(100);
//    }
//
//    private void releaseRight(ActionEvent event) {
//        Bitacora.getInstance().write("RASPI1 FORWARED!!!", Level.INFO);
//        engineManager.forward(0);
//    }
//
//    private void pressedReverse(ActionEvent event) {
//        Bitacora.getInstance().write("RASPI1 FORWARED!!!", Level.INFO);
//        engineManager.forward(100);
//    }
//
//    private void releasedReverse(ActionEvent event) {
//        Bitacora.getInstance().write("RASPI1 FORWARED!!!", Level.INFO);
//        engineManager.forward(0);
//    }
//
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void realeaseLeft(MouseEvent event) {
        Bitacora.getInstance().write("RASPI1 STOP LEFT!!!", Level.INFO);
        engineManager.stop();
    }

    @FXML
    private void pressedLeft(MouseEvent event) {
         Bitacora.getInstance().write("RASPI1 LEFT!!!", Level.INFO);
        engineManager.left(100);
    }

    @FXML
    private void releasedForward(MouseEvent event) {
        Bitacora.getInstance().write("RASPI1 STOP FORWARD!!!", Level.INFO);
        engineManager.stop();
    }

    @FXML
    private void pressedForward(MouseEvent event) {
         Bitacora.getInstance().write("RASPI1 FORWARD!!!", Level.INFO);
        engineManager.forward(100);
    }

    @FXML
    private void releaseRight(MouseEvent event) {
        Bitacora.getInstance().write("RASPI1 STOP RIGHT!!!", Level.INFO);
        engineManager.stop();
    }

    @FXML
    private void pressedRight(MouseEvent event) {
        Bitacora.getInstance().write("RASPI1 RIGHT!!!", Level.INFO);
        engineManager.right(100);
    }

    @FXML
    private void releasedReverse(MouseEvent event) {
        Bitacora.getInstance().write("RASPI1 STOP REVERSE!!!", Level.INFO);
        engineManager.stop();
    }

    @FXML
    private void pressedReverse(MouseEvent event) {
        Bitacora.getInstance().write("RASPI1 REVERSE!!!", Level.INFO);
        engineManager.reverse(100);
    }
}
