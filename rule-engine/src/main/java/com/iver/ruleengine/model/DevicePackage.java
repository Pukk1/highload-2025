package com.iver.ruleengine.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DevicePackage {

    private String id;
    private Integer deviceId;

    public DevicePackage(String id, Integer deviceId) {
        this.id = id;
        this.deviceId = deviceId;
    }
}
