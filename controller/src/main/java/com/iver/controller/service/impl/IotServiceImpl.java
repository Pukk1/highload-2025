package com.iver.controller.service.impl;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.iver.controller.enums.RabbitMqMetaMessageMetaKeys;
import com.iver.controller.exception.CustomException;
import com.iver.controller.model.DevicePackage;
import com.iver.controller.repository.DevicePackageRepository;
import com.iver.controller.service.IotService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IotServiceImpl implements IotService {

    private final RabbitTemplate rabbitTemplate;

    private final DevicePackageRepository devicePackageRepository;

    @Override
    public void processDeviceData(String deviceId, String deviceData) {
        var deviceDataJson = validateDeviceData(deviceData);

        var model = save(new DevicePackage(deviceId, deviceDataJson.toString()));

        send(deviceDataJson, model);
    }

    private DevicePackage save(DevicePackage newDevicePackage) {
        return devicePackageRepository.save(newDevicePackage);
    }

    private void send(JsonObject data, DevicePackage model) {
        var jsonMeta = new JsonObject();
        jsonMeta.addProperty(RabbitMqMetaMessageMetaKeys.ID.getKey(), model.getId());
        jsonMeta.addProperty(RabbitMqMetaMessageMetaKeys.DEVICE_ID.getKey(), model.getDeviceId());
        jsonMeta.add(RabbitMqMetaMessageMetaKeys.DATA.getKey(), data);
        rabbitTemplate.convertAndSend(jsonMeta.toString());
    }

    private JsonObject validateDeviceData(String input) {
        JsonObject jsonObject = JsonParser.parseString(input).getAsJsonObject();

        if (jsonObject.isJsonNull()) {
            throw new CustomException("input json is null");
        }
        if (!jsonObject.isJsonObject()) {
            throw new CustomException("input json is not json object");
        }
        return jsonObject;
    }
}
