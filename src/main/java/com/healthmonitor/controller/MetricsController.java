package com.healthmonitor.controller;

import com.healthmonitor.model.CpuMetrics;
import com.healthmonitor.model.DiskMetrics;
import com.healthmonitor.model.MemoryMetrics;
import com.healthmonitor.model.SystemSnapshot;
import com.healthmonitor.service.MetricsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class MetricsController {

    private final MetricsService metricsService;

    public MetricsController(MetricsService metricsService) {
        this.metricsService = metricsService;
    }

    // GET /api/health → simple status check
    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        return ResponseEntity.ok(Map.of("status", "UP", "service", "health-monitor"));
    }

    // GET /api/metrics/cpu
    @GetMapping("/metrics/cpu")
    public ResponseEntity<CpuMetrics> getCpu() {
        return ResponseEntity.ok(metricsService.getCpuMetrics());
    }

    // GET /api/metrics/memory
    @GetMapping("/metrics/memory")
    public ResponseEntity<MemoryMetrics> getMemory() {
        return ResponseEntity.ok(metricsService.getMemoryMetrics());
    }

    // GET /api/metrics/disk
    @GetMapping("/metrics/disk")
    public ResponseEntity<List<DiskMetrics>> getDisk() {
        return ResponseEntity.ok(metricsService.getDiskMetrics());
    }

    // GET /api/metrics/all → full system snapshot
    @GetMapping("/metrics/all")
    public ResponseEntity<SystemSnapshot> getAll() {
        return ResponseEntity.ok(metricsService.getSystemSnapshot());
    }
}
