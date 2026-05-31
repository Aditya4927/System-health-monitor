package com.healthmonitor.service;

import com.healthmonitor.model.CpuMetrics;
import com.healthmonitor.model.DiskMetrics;
import com.healthmonitor.model.MemoryMetrics;
import com.healthmonitor.model.SystemSnapshot;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.software.os.OSFileStore;
import oshi.software.os.OperatingSystem;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MetricsService {

    private final SystemInfo systemInfo = new SystemInfo();
    private final HardwareAbstractionLayer hardware = systemInfo.getHardware();
    private final OperatingSystem os = systemInfo.getOperatingSystem();

    // ── CPU ──────────────────────────────────────────────────────────────────
    public CpuMetrics getCpuMetrics() {
        CentralProcessor processor = hardware.getProcessor();

        // Two ticks needed to calculate CPU load
        long[] prevTicks = processor.getSystemCpuLoadTicks();
        try { Thread.sleep(500); } catch (InterruptedException ignored) {}

        double cpuLoad = processor.getSystemCpuLoadBetweenTicks(prevTicks) * 100;

        return CpuMetrics.builder()
                .usagePercent(Math.round(cpuLoad * 100.0) / 100.0)
                .physicalCores(processor.getPhysicalProcessorCount())
                .logicalCores(processor.getLogicalProcessorCount())
                .processorName(processor.getProcessorIdentifier().getName())
                .build();
    }

    // ── Memory ───────────────────────────────────────────────────────────────
    public MemoryMetrics getMemoryMetrics() {
        GlobalMemory memory = hardware.getMemory();

        long totalBytes     = memory.getTotal();
        long availableBytes = memory.getAvailable();
        long usedBytes      = totalBytes - availableBytes;

        return MemoryMetrics.builder()
                .totalGB(toGB(totalBytes))
                .usedGB(toGB(usedBytes))
                .availableGB(toGB(availableBytes))
                .usagePercent(Math.round((double) usedBytes / totalBytes * 10000.0) / 100.0)
                .build();
    }

    // ── Disk ─────────────────────────────────────────────────────────────────
    public List<DiskMetrics> getDiskMetrics() {
        return os.getFileSystem().getFileStores().stream()
                .map(store -> {
                    long total = store.getTotalSpace();
                    long free  = store.getUsableSpace();
                    long used  = total - free;
                    return DiskMetrics.builder()
                            .mount(store.getMount())
                            .totalGB(toGB(total))
                            .usedGB(toGB(used))
                            .freeGB(toGB(free))
                            .usagePercent(total > 0
                                    ? Math.round((double) used / total * 10000.0) / 100.0
                                    : 0.0)
                            .build();
                })
                .collect(Collectors.toList());
    }

    // ── Full snapshot ────────────────────────────────────────────────────────
    public SystemSnapshot getSystemSnapshot() {
        return SystemSnapshot.builder()
                .timestamp(LocalDateTime.now())
                .status("UP")
                .cpu(getCpuMetrics())
                .memory(getMemoryMetrics())
                .disks(getDiskMetrics())
                .build();
    }

    // ── Helper ───────────────────────────────────────────────────────────────
    private long toGB(long bytes) {
        return bytes / (1024 * 1024 * 1024);
    }
}
