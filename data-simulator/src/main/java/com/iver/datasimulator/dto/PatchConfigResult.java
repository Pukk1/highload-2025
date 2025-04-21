package com.iver.datasimulator.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatchConfigResult {
    private Integer deviceNumber;
    private Integer messagePerSecond;
}
