package com.healthmonitor.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SystemSnapshot {
    private LocalDateTime timestamp;
    private String status;
    private CpuMetrics cpu;
    private MemoryMetrics memory;
    private List<DiskMetrics> disks;
}
