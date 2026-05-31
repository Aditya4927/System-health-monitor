package com.healthmonitor.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemoryMetrics {
    private long totalGB;
    private long usedGB;
    private long availableGB;
    private double usagePercent;
}
