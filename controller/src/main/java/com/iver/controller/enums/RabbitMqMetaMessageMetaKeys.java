package com.iver.controller.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RabbitMqMetaMessageMetaKeys {
    ID("id"), DEVICE_ID("deviceId"), DATA("data");

    private final String key;
}
