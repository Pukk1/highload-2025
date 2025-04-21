package com.iver.controller.controller;

import com.iver.controller.service.IotService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/iot")
public class IotController {
    private final IotService iotService;

    @PostMapping("/device/{deviceId}/data")
    public ResponseEntity<?> executeIotPackage(@PathVariable String deviceId, @RequestBody String deviceData) {
        iotService.processDeviceData(deviceId, deviceData);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
