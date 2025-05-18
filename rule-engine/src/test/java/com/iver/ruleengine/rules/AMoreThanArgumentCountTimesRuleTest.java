package com.iver.ruleengine.rules;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.iver.ruleengine.model.DevicePackage;
import com.iver.ruleengine.repository.RuleChecksRepository;
import com.iver.ruleengine.rules.AMoreThanArgumentCountTimesRule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AMoreThanArgumentCountTimesRuleTest {
    private RuleChecksRepository ruleChecksRepository;
    private AMoreThanArgumentCountTimesRule rule;

    @BeforeEach
    public void setUp() {
        ruleChecksRepository = Mockito.mock(RuleChecksRepository.class);
        rule = new AMoreThanArgumentCountTimesRule(10, 3, ruleChecksRepository);
    }

    @Test
    public void testCheckRule_valueNotGreaterThanArgument_shouldReturnFalse() {
        Map<String, JsonElement> data = Map.of("A", new JsonPrimitive("5"));

        boolean result = rule.checkRule(new DevicePackage("123", 1), data);

        assertFalse(result);
        verify(ruleChecksRepository, never()).findLastByRuleName(any(), any());
    }

    @Test
    public void testCheckRule_valueIsNotNumeric_shouldReturnFalse() {
        Map<String, JsonElement> data = Map.of("A", new JsonPrimitive("not_a_number"));

        boolean result = rule.checkRule(new DevicePackage("123", 1), data);

        assertFalse(result);
        verify(ruleChecksRepository, never()).findLastByRuleName(any(), any());
    }

    @Test
    public void testCheckRule_keyNotPresent_shouldReturnFalse() {
        Map<String, JsonElement> data = Map.of("B", new JsonPrimitive("15"));

        boolean result = rule.checkRule(new DevicePackage("123", 1), data);

        assertFalse(result);
        verify(ruleChecksRepository, never()).findLastByRuleName(any(), any());
    }

    // Вспомогательный класс для RuleCheck, если его нет в коде проекта
    static class RuleCheck {
        private final boolean result;

        public RuleCheck(boolean result) {
            this.result = result;
        }

        public boolean getResult() {
            return result;
        }
    }
}