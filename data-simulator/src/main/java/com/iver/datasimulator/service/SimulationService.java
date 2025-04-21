package com.iver.datasimulator.service;

import com.iver.datasimulator.dto.PatchConfigInput;
import com.iver.datasimulator.dto.PatchConfigResult;

public interface SimulationService {
    PatchConfigResult setSimulationConfig(PatchConfigInput patchConfigInput);
}
