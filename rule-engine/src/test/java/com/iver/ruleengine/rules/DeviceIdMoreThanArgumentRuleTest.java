package com.iver.ruleengine.rules;

import com.iver.ruleengine.model.DevicePackage;
import com.iver.ruleengine.rules.DeviceIdMoreThanArgumentRule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class DeviceIdMoreThanArgumentRuleTest {
    private DeviceIdMoreThanArgumentRule rule;

    @BeforeEach
    public void setUp() {
        // Устанавливаем аргумент, с которым будет сравниваться deviceId
        rule = new DeviceIdMoreThanArgumentRule(100);
    }

    @Test
    public void testCheckRule_DeviceIdGreaterThanArgument_ShouldReturnTrue() {
        DevicePackage devicePackage = new DevicePackage("1", 150);

        boolean result = rule.checkRule(devicePackage, Map.of());

        assertTrue(result);
    }

    @Test
    public void testCheckRule_DeviceIdEqualToArgument_ShouldReturnFalse() {
        DevicePackage devicePackage = new DevicePackage("1", 100);

        boolean result = rule.checkRule(devicePackage, Map.of());

        assertFalse(result);
    }

    @Test
    public void testCheckRule_DeviceIdLessThanArgument_ShouldReturnFalse() {
        DevicePackage devicePackage = new DevicePackage("1", 50);

        boolean result = rule.checkRule(devicePackage, Map.of());

        assertFalse(result);
    }
}