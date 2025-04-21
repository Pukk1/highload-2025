package com.iver.datasimulator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class DataSimulatorApplication {

    public static void main(String[] args) {
        SpringApplication.run(DataSimulatorApplication.class, args);
    }

}
