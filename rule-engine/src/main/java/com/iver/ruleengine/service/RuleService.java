package com.iver.ruleengine.service;

import com.google.gson.JsonElement;
import com.iver.ruleengine.model.DevicePackage;

import java.util.Map;

public interface RuleService {

    void checkAllRulesForMessage(DevicePackage devicePackage, Map<String, JsonElement> data);
}
