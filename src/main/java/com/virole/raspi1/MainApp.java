package com.virole.raspi1;

import com.ia.config.ConfigManager;
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.virole.bitacora.Bitacora;
import java.util.logging.Level;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Bitacora.getInstance().write(" RASPI1 started.", Level.INFO);
        final GpioController gpio;
        try {
            gpio = GpioFactory.getInstance();
            ConfigManager.getInstance().setMode(Raspi1Constants.RASPBERRY_MODE);
            Bitacora.getInstance().write(" RASPI1 started in RASPBERRY MODE.", Level.INFO);
        } catch(Throwable ex) {
            ConfigManager.getInstance().setMode(Raspi1Constants.SIMULATION_MODE);
            Bitacora.getInstance().write(" RASPI1 started in SIMULATION MODE: " + ex.getMessage(), Level.INFO);
        }
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Scene.fxml"));
        
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/Styles.css");
        
        stage.setTitle("RASPI1 DASHBOARD");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
