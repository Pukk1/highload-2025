package com.iver.ruleengine.repository;

import com.iver.ruleengine.model.RuleCheck;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface RuleChecksRepository extends MongoRepository<RuleCheck, String> {

    @Query(value = "{ 'ruleName': ?0 }", sort = "{ 'createdAt': -1 }")
    List<RuleCheck> findLastByRuleName(String ruleName, Pageable pageable);
}
