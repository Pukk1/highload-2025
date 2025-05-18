package com.iver.ruleengine.rules;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.iver.ruleengine.model.DevicePackage;
import com.iver.ruleengine.rules.AMoreThanArgumentRule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class AMoreThanArgumentRuleTest {
    private AMoreThanArgumentRule rule;

    @BeforeEach
    public void setUp() {
        // Задаём аргумент, с которым будет сравниваться значение A
        rule = new AMoreThanArgumentRule(10);
    }

    @Test
    public void testCheckRule_ValueGreaterThanArgument_ShouldReturnTrue() {
        Map<String, JsonElement> data = Map.of("A", new JsonPrimitive("15"));

        boolean result = rule.checkRule(new DevicePackage("1", 1), data);

        assertTrue(result);
    }

    @Test
    public void testCheckRule_ValueEqualToArgument_ShouldReturnFalse() {
        Map<String, JsonElement> data = Map.of("A", new JsonPrimitive("10"));

        boolean result = rule.checkRule(new DevicePackage("1", 1), data);

        assertFalse(result);
    }

    @Test
    public void testCheckRule_ValueLessThanArgument_ShouldReturnFalse() {
        Map<String, JsonElement> data = Map.of("A", new JsonPrimitive("5"));

        boolean result = rule.checkRule(new DevicePackage("1", 1), data);

        assertFalse(result);
    }

    @Test
    public void testCheckRule_KeyNotPresent_ShouldReturnFalse() {
        Map<String, JsonElement> data = Map.of("B", new JsonPrimitive("15"));

        boolean result = rule.checkRule(new DevicePackage("1", 1), data);

        assertFalse(result);
    }

    @Test
    public void testCheckRule_ValueIsNotNumber_ShouldReturnFalse() {
        Map<String, JsonElement> data = Map.of("A", new JsonPrimitive("not_a_number"));

        boolean result = rule.checkRule(new DevicePackage("1", 1), data);

        assertFalse(result);
    }
}