package com.empresa.registraduria.util;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class CargarConfig {

    private Properties properties = new Properties();
    private final String rutaConfig;

    public CargarConfig(String rutaConfig) {
        this.rutaConfig = rutaConfig;
    }

    public Properties cargarProperties() throws IOException {
        properties.load(new FileReader(rutaConfig));
        return properties;
    }

}
