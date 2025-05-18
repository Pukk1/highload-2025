package com.iver.ruleengine.listener;

import com.iver.ruleengine.AbstractIntegrationTest;
import com.iver.ruleengine.repository.RuleChecksRepository;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

class IotListenerIntegrationTest extends AbstractIntegrationTest {


    @Autowired
    IotListener iotListener;

    @Autowired
    RuleChecksRepository ruleChecksRepository;

    @Test
    void testValid_validValue_shouldWorkRight() {
        
        var validMessage = "{\"id\":\"6829fb25ed6cbb29fc12efc2\",\"deviceId\":\"123\",\"data\":{\"temperature\":25}}";
        iotListener.listen(validMessage);

        assertEquals(ruleChecksRepository.count(), 3);
    }
}