package com.iver.controller.config;

import com.iver.controller.config.properties.RabbitMqConfigurationProperties;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    private final String queueName;

    public RabbitMQConfig(RabbitMqConfigurationProperties rabbitMqConfigurationProperties) {
        this.queueName = rabbitMqConfigurationProperties.getQueueName();
    }

    @Bean
    public Queue productQueue() {
        return new Queue(queueName, true);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setRoutingKey(queueName);
        return rabbitTemplate;
    }
}
