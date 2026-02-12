package com.dragon.content.dev1sme.event;

import lombok.Getter;
import lombok.Setter;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
// thonk
// import static server.Manager.EVENT_SEVER;

/**
 * @author dev1sme
 */
@Setter
@Getter
public class ConfigEvent {
    private String event;
    
    public ConfigEvent() {
        load();
    }
 
    private void load() {
        Properties properties = new Properties();
        try (FileInputStream fis = new FileInputStream("config/event.properties")) {
            properties.load(fis);
            properties.forEach((k, v) -> {
                //thonk khnog hieu o day de lam gi
            });
            event = String.valueOf(properties.getProperty("event"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

