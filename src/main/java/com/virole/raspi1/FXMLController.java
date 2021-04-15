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
import java.util.logging.Level;

public class FXMLController implements Initializable {
    final GpioController gpio;
    final GpioPinDigitalOutput pin;
    private boolean isPinLow;
    
    public FXMLController() {
      // crear controlador gpio
        if(!ConfigManager.getInstance().isModeSimulation()){
            Bitacora.getInstance().write("Creating Controller GPIO", Level.INFO);
            this.gpio = GpioFactory.getInstance();
            Bitacora.getInstance().write(String.format("Creating PIN GPIO: '%s'", RaspiPin.GPIO_07.getName()), Level.INFO);
            this.pin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_07, "MyLED", PinState.HIGH);
        } else{
            Bitacora.getInstance().write("Creating Controller MOCK GPIO", Level.INFO);
            this.gpio = MockGpioFactory.getInstance();
            Bitacora.getInstance().write(String.format("Creating PIN GPIO: '%s'", RaspiPin.GPIO_07.getName()), Level.INFO);
            this.pin = gpio.provisionDigitalOutputPin(MockPin.DIGITAL_OUTPUT_PIN_GPIO7, "MyLED", PinState.HIGH);
            
        }
 
        isPinLow = true;
        pin.low();
    }

    
    
    @FXML
    private Label label;

    @FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");

        if(isPinLow){
            isPinLow = false;
            pin.high();
            label.setText("Encendida!!!");
            Bitacora.getInstance().write(String.format("Led '%s' encendido!!!", pin.getName()), Level.INFO);
        } else {
            isPinLow = true;
            pin.low();
            label.setText("Apagada!!!");
            Bitacora.getInstance().write(String.format("Led '%s' apagado!!!", pin.getName()), Level.INFO);
        }
        
        
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
}
