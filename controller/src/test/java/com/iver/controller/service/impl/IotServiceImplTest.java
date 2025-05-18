package com.iver.controller.service.impl;

import com.iver.controller.exception.CustomException;
import com.iver.controller.model.DevicePackage;
import com.iver.controller.repository.DevicePackageRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IotServiceImplTest {

    @Mock
    private RabbitTemplate rabbitTemplate;

    @Mock
    private DevicePackageRepository devicePackageRepository;

    @InjectMocks
    private IotServiceImpl iotService;

    private final String deviceId = "device-123";

    @Test
    void processDeviceData_validInput_success() {
        // arrange
        String validJson = "{\"temperature\": 25}";
        DevicePackage savedPackage = new DevicePackage(deviceId, validJson);
        savedPackage.setId("1L");

        when(devicePackageRepository.save(any())).thenReturn(savedPackage);

        // act
        iotService.processDeviceData(deviceId, validJson);

        // assert
        verify(devicePackageRepository).save(any(DevicePackage.class));
        verify(rabbitTemplate).convertAndSend(anyString());
    }

    @Test
    void processDeviceData_notJsonObject_throwsException() {
        String invalidJson = "[]";

        CustomException exception = assertThrows(CustomException.class, () ->
                iotService.processDeviceData(deviceId, invalidJson)
        );

        assertEquals("Custom exception: input json is not json object", exception.getMessage());
    }


    @Test
    void processDeviceData_nullJson_throwsException() {
        String invalidJson = "null";

        CustomException exception = assertThrows(CustomException.class, () ->
                iotService.processDeviceData(deviceId, invalidJson)
        );

        assertEquals("Custom exception: input json is null", exception.getMessage());
    }
}
