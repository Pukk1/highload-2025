package com.iver.datasimulator.config.properties;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Set;

@Data
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "data.simulation")
public class DataSimulationProperties {
    private Integer deviceNumber;
    private Integer messagePerSecond;
    private Set<String> availableDevicePropertiesNames;
    private Set<String> possibleDevicePropertiesValues;
}
