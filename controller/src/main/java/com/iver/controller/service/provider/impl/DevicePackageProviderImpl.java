package com.iver.controller.service.provider.impl;

import com.iver.controller.model.DevicePackage;
import com.iver.controller.repository.DevicePackageRepository;
import com.iver.controller.service.provider.DevicePackageProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DevicePackageProviderImpl implements DevicePackageProvider {

    private final DevicePackageRepository devicePackageRepository;

    @Override
    @Cacheable(value = "data", key = "111")
    public DevicePackage saveDevicePackage(DevicePackage newDevicePackage) {
        return devicePackageRepository.save(newDevicePackage);
    }
}
