package com.iver.datasimulator.service.impl;

import com.iver.datasimulator.config.properties.DataSimulationProperties;
import com.iver.datasimulator.dto.PatchConfigInput;
import com.iver.datasimulator.dto.PatchConfigResult;
import com.iver.datasimulator.integration.api.IotControllerApi;
import com.iver.datasimulator.utils.DataBodyGeneratorUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.util.concurrent.ScheduledExecutorService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class SimulationServiceImplTest {
    private IotControllerApi iotControllerApi;
    private DataSimulationProperties properties;
    private DataBodyGeneratorUtil dataBodyGeneratorUtil;

    private SimulationServiceImpl simulationService;

    @BeforeEach
    void setUp() {
        iotControllerApi = mock(IotControllerApi.class);
        properties = mock(DataSimulationProperties.class);
        dataBodyGeneratorUtil = mock(DataBodyGeneratorUtil.class);

        when(properties.getDeviceNumber()).thenReturn(2);
        when(properties.getMessagePerSecond()).thenReturn(1);


        simulationService = new SimulationServiceImpl(iotControllerApi, properties, dataBodyGeneratorUtil);
    }

    @Test
    void testSetSimulationConfig_InitializesExecutorAndReturnsCorrectResult() throws Exception {
        var input = new PatchConfigInput(2, 1);

        Call<String> callMock = mock(Call.class);
        Response<String> responseMock = mock(Response.class);

        when(iotControllerApi.sendData(anyInt(), any())).thenReturn(callMock);
        when(callMock.execute()).thenReturn(responseMock);

        simulationService.afterContextRefreshed();
        PatchConfigResult result = simulationService.setSimulationConfig(input);

        assertEquals(2, result.getDeviceNumber());
        assertEquals(1, result.getMessagePerSecond());

        // Optional: проверка, что метод был вызван хотя бы один раз
        verify(iotControllerApi, timeout(5000).atLeastOnce()).sendData(anyInt(), any());
    }
}