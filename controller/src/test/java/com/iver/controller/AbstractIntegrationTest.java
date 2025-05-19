//package com.iver.controller;
//
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.DynamicPropertyRegistry;
//import org.springframework.test.context.DynamicPropertySource;
//import org.testcontainers.containers.MongoDBContainer;
//import org.testcontainers.containers.RabbitMQContainer;
//import org.testcontainers.junit.jupiter.Container;
//import org.testcontainers.junit.jupiter.Testcontainers;
//
//@SpringBootTest
//@Testcontainers
//public class AbstractIntegrationTest {
//
//    @Container
//    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:6.0");
//    @Container
//    static RabbitMQContainer rabbitMQContainer = new RabbitMQContainer("rabbitmq:3.12-management");
//
//    @DynamicPropertySource
//    static void setMongoProps(DynamicPropertyRegistry registry) {
//        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
//
//        registry.add("spring.rabbitmq.host", rabbitMQContainer::getHost);
//        registry.add("spring.rabbitmq.port", rabbitMQContainer::getAmqpPort);
//        registry.add("spring.rabbitmq.username", rabbitMQContainer::getAdminUsername);
//        registry.add("spring.rabbitmq.password", rabbitMQContainer::getAdminPassword);
//    }
//}
