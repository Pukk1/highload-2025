package com.iver.controller.service;

import jakarta.validation.constraints.NotBlank;

public interface IotService {
    void processDeviceData(@NotBlank(message = "deviceId (path parameter) should not be blank") String deviceId,
            @NotBlank(message = "deviceData (request body) should not be blank") String deviceData);
}
