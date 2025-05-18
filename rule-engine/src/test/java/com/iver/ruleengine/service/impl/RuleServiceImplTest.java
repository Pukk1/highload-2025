package com.iver.ruleengine.service.impl;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.iver.ruleengine.model.DevicePackage;
import com.iver.ruleengine.model.RuleCheck;
import com.iver.ruleengine.repository.RuleChecksRepository;
import com.iver.ruleengine.rules.Rule;
import com.iver.ruleengine.service.impl.RuleServiceImpl;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.mockito.Mockito.*;

class RuleServiceImplTest {
    private RuleChecksRepository ruleChecksRepository;
    private MeterRegistry meterRegistry;
    private Rule ruleMock;
    private RuleServiceImpl ruleService;
    private Counter counter;

    @BeforeEach
    public void setUp() {
        ruleChecksRepository = mock(RuleChecksRepository.class);
        ruleMock = mock(Rule.class);
        meterRegistry = mock(MeterRegistry.class);
        counter = mock(Counter.class);

        when(meterRegistry.counter("trust_result")).thenReturn(counter);

        Map<String, Rule> rules = Map.of("TestRule", ruleMock);
        ruleService = new RuleServiceImpl(ruleChecksRepository, rules, meterRegistry);
    }

    @Test
    public void testCheckAllRulesForMessage_PositiveResult() {
        // Arrange
        DevicePackage devicePackage = new DevicePackage("1", 42);
        Map<String, JsonElement> data = Map.of("A", new JsonPrimitive("100"));

        when(ruleMock.checkRule(devicePackage, data)).thenReturn(true);

        // Act
        ruleService.checkAllRulesForMessage(devicePackage, data);

        // Assert
        verify(ruleMock).checkRule(devicePackage, data);
        verify(ruleChecksRepository).save(argThat(ruleCheck ->
                ruleCheck.getDeviceId().equals(42) &&
                        ruleCheck.getRuleName().equals("TestRule") &&
                        ruleCheck.getResult())
        );
        verify(counter).increment();
    }

    @Test
    public void testCheckAllRulesForMessage_NegativeResult() {
        DevicePackage devicePackage = new DevicePackage("2", 99);
        Map<String, JsonElement> data = Map.of("A", new JsonPrimitive("5"));

        when(ruleMock.checkRule(devicePackage, data)).thenReturn(false);

        ruleService.checkAllRulesForMessage(devicePackage, data);

        verify(ruleMock).checkRule(devicePackage, data);
        verify(ruleChecksRepository).save(argThat(ruleCheck ->
                ruleCheck.getDeviceId().equals(99) &&
                        ruleCheck.getRuleName().equals("TestRule") &&
                        !ruleCheck.getResult())
        );
        verify(counter, never()).increment();
    }
}