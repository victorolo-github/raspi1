/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.virole.bitacora;

import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 *
 * @author ulabvrl
 */
public class Bitacora {

    private final Logger LOG_RAIZ = Logger.getLogger("bitacora");
    private static Bitacora instance = null;
    private Handler fileHandler;

    public Bitacora() {
        try {
            fileHandler = new FileHandler("./bitacora.log", false);
            // El formateador indica como presentar los datos, en este caso usaremos el formaro sencillo
            // el cual es mas facil de leer, si no usamos esto el log estara en formato xml por defecto
            SimpleFormatter simpleFormatter = new SimpleFormatter();
            
            LOG_RAIZ.addHandler(fileHandler);
            
            // Indicamos a partir de que nivel deseamos mostrar los logs, podemos especificar un nivel en especifico
            // para ignorar informacion que no necesitemos
            fileHandler.setLevel(Level.ALL);

            // Se especifica que formateador usara el manejador (handler) de archivo
            fileHandler.setFormatter(simpleFormatter);
            
            LOG_RAIZ.setLevel(Level.ALL);
            
             
        } catch (IOException ex) {
            Logger.getLogger(Bitacora.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(Bitacora.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static synchronized Bitacora getInstance() {
        if (null == instance) {
            instance = new Bitacora();
        }
        return instance;
    }
    
    public void write(String msg, Level level){
        LOG_RAIZ.log(level, msg);
    }
}
