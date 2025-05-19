//package com.iver.controller.controller;
//
//import com.iver.controller.AbstractIntegrationTest;
//import com.iver.controller.model.DevicePackage;
//import com.iver.controller.repository.DevicePackageRepository;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.transaction.annotation.Transactional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//class IotControllerIntegrationTest extends AbstractIntegrationTest {
//
//    @Autowired
//    DevicePackageRepository devicePackageRepository;
//
//    @Autowired
//    IotController iotController;
//
//    @Test
//    void processDeviceData_validInput_success() {
//        String validJson = "{\"temperature\":25}";
//        String deviceId = "device-123";
//
//        iotController.executeIotPackage(deviceId, validJson);
//
//        var allSavedPackages = devicePackageRepository.findAll();
//
//        assertEquals(allSavedPackages.size(), 1);
//        assertEquals(allSavedPackages.getFirst().getDeviceId(), deviceId);
//        assertEquals(allSavedPackages.getFirst().getDeviceData(), validJson);
//    }
//}