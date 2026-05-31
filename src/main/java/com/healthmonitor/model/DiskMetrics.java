package com.healthmonitor.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DiskMetrics {
    private String mount;
    private long totalGB;
    private long usedGB;
    private long freeGB;
    private double usagePercent;
}
