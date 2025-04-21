package com.iver.datasimulator.service.impl;

import com.google.gson.JsonObject;
import com.iver.datasimulator.config.properties.DataSimulationProperties;
import com.iver.datasimulator.dto.PatchConfigInput;
import com.iver.datasimulator.dto.PatchConfigResult;
import com.iver.datasimulator.integration.api.IotControllerApi;
import com.iver.datasimulator.service.SimulationService;
import com.iver.datasimulator.utils.DataBodyGeneratorUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class SimulationServiceImpl implements SimulationService {

    private final IotControllerApi iotControllerApi;
    private final DataSimulationProperties dataSimulationProperties;
    private final DataBodyGeneratorUtil dataBodyGeneratorUtil;
    private ScheduledExecutorService executor;

    @EventListener(classes = { ContextRefreshedEvent.class })
    public void afterContextRefreshed() {
        executor = Executors.newScheduledThreadPool(dataSimulationProperties.getDeviceNumber());
        setSimulationConfig(new PatchConfigInput(dataSimulationProperties.getDeviceNumber(),
                dataSimulationProperties.getMessagePerSecond()));
    }

    @Override
    public PatchConfigResult setSimulationConfig(PatchConfigInput patchConfigInput) {
        var newMessagePerSecond = patchConfigInput.getMessagePerSecond();
        var newDeviceNumber = patchConfigInput.getDeviceNumber();
        if (((ThreadPoolExecutor) executor).getPoolSize() != newDeviceNumber) {
            executor.close();
            executor = Executors.newScheduledThreadPool(newDeviceNumber);
        }
        for (int i = 0; i < newDeviceNumber; i++) {
            final int deviceNumber = i;
            executor.scheduleAtFixedRate(() -> {
                try {
                    var res = iotControllerApi
                            .sendData(deviceNumber, dataBodyGeneratorUtil.generateDataBody(deviceNumber)).execute();
                    System.out.println(res);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }, 3000, 1000 / newMessagePerSecond, TimeUnit.MILLISECONDS);
        }
        return new PatchConfigResult(newDeviceNumber, newMessagePerSecond);
    }
}
