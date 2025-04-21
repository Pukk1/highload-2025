package com.iver.ruleengine.listener;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.iver.ruleengine.model.DevicePackage;
import com.iver.ruleengine.service.RuleService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class IotListener {

    private final RuleService ruleService;

    @RabbitListener(queues = "${spring.rabbitmq.queue}")
    public void listen(String devicePackageMessage) {
        JsonObject json = JsonParser.parseString(devicePackageMessage).getAsJsonObject();

        String id = json.getAsJsonPrimitive("id").getAsString();
        int deviceId = json.getAsJsonPrimitive("deviceId").getAsInt();
        var data = json.getAsJsonObject("data").asMap();

        DevicePackage devicePackage = new DevicePackage(id, deviceId);
        ruleService.checkAllRulesForMessage(devicePackage, data);
    }
}
