package com.iver.datasimulator.utils.impl;

import com.google.gson.JsonObject;
import com.iver.datasimulator.config.properties.DataSimulationProperties;
import com.iver.datasimulator.utils.DataBodyGeneratorUtil;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;
import java.util.Set;

@Component
public class DataBodyGeneratorUtilImpl implements DataBodyGeneratorUtil {

    private final Set<String> availableDevicePropertiesNames;
    private final Set<String> possibleDevicePropertiesValues;

    public DataBodyGeneratorUtilImpl(DataSimulationProperties dataSimulationProperties) {
        this.availableDevicePropertiesNames = dataSimulationProperties.getAvailableDevicePropertiesNames();
        this.possibleDevicePropertiesValues = dataSimulationProperties.getPossibleDevicePropertiesValues();
    }

    @Override
    public JsonObject generateDataBody(int deviceNumber) {
        final JsonObject body = new JsonObject();

        for (var deviceProperty : getDeviceProperties(deviceNumber)) {
            body.addProperty(deviceProperty, getRandomPropertyValue());
        }
        return body;
    }

    /**
     * выдаёт намор свойств которые будут отправлены в теле запроса
     *
     * @param deviceNumber
     *            - номер девайса, для которого генерируется тело
     */
    private List<String> getDeviceProperties(int deviceNumber) {
        var devicePropertiesNumber = deviceNumber % availableDevicePropertiesNames.size() + 1;
        return availableDevicePropertiesNames.stream().limit(devicePropertiesNumber).toList();

    }

    private String getRandomPropertyValue() {
        int elementNumber = new Random().nextInt(0, possibleDevicePropertiesValues.size());
        return possibleDevicePropertiesValues.stream().skip(elementNumber).toList().getFirst();
    }
}
