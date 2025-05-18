package com.iver.controller.repository;

import com.iver.controller.model.DevicePackage;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface DevicePackageRepository extends MongoRepository<DevicePackage, String> {
}
