package com.iver.ruleengine.rules;

import com.google.gson.JsonElement;
import com.iver.ruleengine.model.DevicePackage;

import java.util.Map;

public interface Rule {

    boolean checkRule(DevicePackage devicePackage, Map<String, JsonElement> data);
}
