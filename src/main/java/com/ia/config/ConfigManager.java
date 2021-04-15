/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ia.config;

import com.virole.raspi1.Raspi1Constants;

/**
 *
 * @author ulabvrl
 */
public class ConfigManager {
    
    private static ConfigManager instance = null;
    private int mode;
    
    public ConfigManager() {
        
    }
    
    public static synchronized ConfigManager getInstance(){
        if(instance == null){
            instance = new ConfigManager();
        }
        
        return instance;
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }
    
    
    public boolean isModeSimulation(){
        return mode == Raspi1Constants.SIMULATION_MODE;
    }
    
}
