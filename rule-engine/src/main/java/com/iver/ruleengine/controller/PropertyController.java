package com.iver.ruleengine.controller;

import com.iver.ruleengine.service.infrastructure.PropertyUpdaterService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/properties")
public class PropertyController {

    private final PropertyUpdaterService propertyUpdaterService;

    public PropertyController(PropertyUpdaterService propertyUpdaterService) {
        this.propertyUpdaterService = propertyUpdaterService;
    }

    @PostMapping("/update")
    public String updateProperty(@RequestParam String key, @RequestParam String value) {
        propertyUpdaterService.updateProperty(key, value);
        return "Property updated. Remember to call the actuator /actuator/refresh";
    }
}
