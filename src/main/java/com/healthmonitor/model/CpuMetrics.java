package com.healthmonitor.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CpuMetrics {
    private double usagePercent;
    private int physicalCores;
    private int logicalCores;
    private String processorName;
}
