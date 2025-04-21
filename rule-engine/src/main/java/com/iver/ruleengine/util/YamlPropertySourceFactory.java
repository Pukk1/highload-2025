package com.iver.ruleengine.util;

import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.DefaultPropertySourceFactory;
import org.springframework.core.io.support.EncodedResource;

import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

public class YamlPropertySourceFactory extends DefaultPropertySourceFactory {

    @Override
    @Nonnull
    public PropertySource<?> createPropertySource(String name, EncodedResource resource) throws IOException {
        Resource yamlResource = resource.getResource();
        if (!yamlResource.exists()) {
            return super.createPropertySource(name, resource);
        }

        YamlPropertiesFactoryBean factory = new YamlPropertiesFactoryBean();
        factory.setResources(yamlResource);
        Properties properties = factory.getObject();

        Objects.requireNonNull(properties, "Failed to load properties from YAML file: " + yamlResource.getFilename());
        return new PropertiesPropertySource(Objects.requireNonNull(yamlResource.getFilename()), properties);
    }
}
