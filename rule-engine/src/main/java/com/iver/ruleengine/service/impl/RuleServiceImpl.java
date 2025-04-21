package com.iver.ruleengine.service.impl;

import com.google.gson.JsonElement;
import com.iver.ruleengine.model.DevicePackage;
import com.iver.ruleengine.model.RuleCheck;
import com.iver.ruleengine.repository.RuleChecksRepository;
import com.iver.ruleengine.rules.Rule;
import com.iver.ruleengine.service.RuleService;
import io.micrometer.core.instrument.MeterRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class RuleServiceImpl implements RuleService {

    private final Map<String, Rule> rules;
    private final RuleChecksRepository ruleChecksRepository;
    private final MeterRegistry meterRegistry;
    private static final Logger logger = LoggerFactory.getLogger(RuleServiceImpl.class);

    @Autowired
    public RuleServiceImpl(RuleChecksRepository ruleChecksRepository, Map<String, Rule> rules,
            MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
        this.ruleChecksRepository = ruleChecksRepository;
        this.rules = rules;
    }

    @Override
    public void checkAllRulesForMessage(DevicePackage devicePackage, Map<String, JsonElement> data) {
        rules.forEach((k, v) -> {
            boolean result = v.checkRule(devicePackage, data);
            RuleCheck ruleCheck = new RuleCheck(devicePackage.getDeviceId(), k, result);
            ruleChecksRepository.save(ruleCheck);
            if (ruleCheck.getResult()) {
                meterRegistry.counter("trust_result").increment();
            }
            logger.info("{} result is {}", k, result);
        });
    }
}
