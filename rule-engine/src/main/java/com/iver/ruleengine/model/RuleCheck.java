package com.iver.ruleengine.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Data
@NoArgsConstructor
@Document(collection = "rule_check_results")
public class RuleCheck {

    private String id;
    private Integer deviceId;
    private String ruleName;
    private Boolean result;
    @CreatedDate
    private Instant createdAt;

    public RuleCheck(Integer deviceId, String ruleName, Boolean result) {
        this.deviceId = deviceId;
        this.ruleName = ruleName;
        this.result = result;
    }
}
