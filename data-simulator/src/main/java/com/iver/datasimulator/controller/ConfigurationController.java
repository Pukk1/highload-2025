package com.iver.datasimulator.controller;

import com.iver.datasimulator.dto.PatchConfigInput;
import com.iver.datasimulator.service.SimulationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/simulation/config")
@RequiredArgsConstructor
public class ConfigurationController {
    private final SimulationService simulationService;

    @PatchMapping
    public ResponseEntity<?> patchConfig(@RequestBody PatchConfigInput patchConfigInput) {
        return ResponseEntity.ok(simulationService.setSimulationConfig(patchConfigInput));
    }
}
