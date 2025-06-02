package com.iver.controller.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Objects;

@Data
@NoArgsConstructor
@Document(collection = "device-packages")
public class DevicePackage implements Serializable {
    @Id
    private String id;
    private String deviceId;
    private String deviceData;

    public DevicePackage(String deviceId, String deviceData) {
        this.deviceId = deviceId;
        this.deviceData = deviceData;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        DevicePackage that = (DevicePackage) o;
        return Objects.equals(id, that.id) && Objects.equals(deviceId, that.deviceId)
                && Objects.equals(deviceData, that.deviceData);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, deviceId, deviceData);
    }
}
