package com.iver.controller.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@Document(collation = "device-packages")
public class DevicePackage {
    @Id
    private String id;
    private String deviceId;
    private String deviceData;

    public DevicePackage(String deviceId, String deviceData) {
        this.deviceId = deviceId;
        this.deviceData = deviceData;
    }
}
