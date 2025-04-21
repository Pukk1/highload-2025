package com.iver.ruleengine.rules;

import com.google.gson.JsonElement;
import com.iver.ruleengine.model.DevicePackage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RefreshScope
public class AMoreThanArgumentRule extends InstantRule<Integer> {

    private final String KEY = "A";

    public AMoreThanArgumentRule(@Value("${rules.instant.aMoreThanArgument}") Integer argument) {
        super(argument);
        this.argument = argument;
    }

    @Override
    public boolean checkRule(DevicePackage devicePackage, Map<String, JsonElement> data) {
        try {
            if (data.containsKey(KEY)) {
                Integer a = data.get(KEY).isJsonPrimitive() ? Integer.parseInt(data.get(KEY).getAsString()) : null;
                return a != null && a > argument;
            }
        } catch (NumberFormatException ignored) {
        }

        return false;
    }
}
