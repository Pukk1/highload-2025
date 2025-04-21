package com.iver.ruleengine.rules;

import com.google.gson.JsonElement;
import com.iver.ruleengine.model.DevicePackage;
import com.iver.ruleengine.repository.RuleChecksRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RefreshScope
public class AMoreThanArgumentCountTimesRule extends LastingRule<Integer, Integer> {

    private final String KEY = "A";
    private final RuleChecksRepository ruleChecksRepository;

    public AMoreThanArgumentCountTimesRule(
            @Value("${rules.lasting.aMoreThanArgumentCountTimes.argument}") Integer argument,
            @Value("${rules.lasting.aMoreThanArgumentCountTimes.count}") Integer count,
            RuleChecksRepository ruleChecksRepository) {
        super(argument, count);
        this.argument = argument;
        this.count = count;
        this.ruleChecksRepository = ruleChecksRepository;
    }

    @Override
    public boolean checkRule(DevicePackage devicePackage, Map<String, JsonElement> data) {
        try {
            if (data.containsKey(KEY)) {
                Integer a = data.get(KEY).isJsonPrimitive() ? Integer.parseInt(data.get(KEY).getAsString()) : null;
                if (a != null && a > argument) {
                    var lastChecks = ruleChecksRepository
                            .findLastByRuleName(AMoreThanArgumentRule.class.getSimpleName(), PageRequest.of(0, count));
                    if (lastChecks == null || lastChecks.isEmpty()) {
                        return false;
                    }
                    for (var check : lastChecks) {
                        if (!check.getResult())
                            return false;
                    }

                    return true;
                }
            }
        } catch (NumberFormatException ignored) {
        }

        return false;
    }
}
