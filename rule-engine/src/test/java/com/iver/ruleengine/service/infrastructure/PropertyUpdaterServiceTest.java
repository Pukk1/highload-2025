package com.iver.ruleengine.service.infrastructure;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class PropertyUpdaterServiceTest {
    private ConfigurableEnvironment environment;
    private MutablePropertySources propertySources;
    private PropertyUpdaterService propertyUpdaterService;

    @BeforeEach
    public void setUp() {
        environment = mock(ConfigurableEnvironment.class);
        propertySources = mock(MutablePropertySources.class);
        when(environment.getPropertySources()).thenReturn(propertySources);

        propertyUpdaterService = new PropertyUpdaterService(environment);
    }

    @Test
    public void testUpdateProperty_AddsNewPropertySource_WhenNotExists() {
        String key = "my.prop";
        String value = "123";

        when(propertySources.contains("dynamicProperties")).thenReturn(false);

        ArgumentCaptor<MapPropertySource> propertySourceCaptor = ArgumentCaptor.forClass(MapPropertySource.class);

        // Выполнение
        propertyUpdaterService.updateProperty(key, value);

        // Проверка, что добавлен новый MapPropertySource с правильными значениями
        verify(propertySources).addFirst(propertySourceCaptor.capture());
        MapPropertySource captured = propertySourceCaptor.getValue();

        assertEquals("dynamicProperties", captured.getName());
        assertEquals("123", captured.getSource().get("my.prop"));
    }
}