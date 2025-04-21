package com.iver.ruleengine.rules;

import com.google.gson.JsonElement;
import com.iver.ruleengine.model.DevicePackage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RefreshScope
public class DeviceIdMoreThanArgumentRule extends InstantRule<Integer> {

    public DeviceIdMoreThanArgumentRule(@Value("${rules.instant.deviceIdMoreThanArgument}") Integer argument) {
        super(argument);
        this.argument = argument;
    }

    @Override
    public boolean checkRule(DevicePackage devicePackage, Map<String, JsonElement> data) {
        return devicePackage.getDeviceId() > argument;
    }
}
