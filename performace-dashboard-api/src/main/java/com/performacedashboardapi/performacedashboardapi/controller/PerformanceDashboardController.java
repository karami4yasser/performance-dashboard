package com.performacedashboardapi.performacedashboardapi.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.performacedashboardapi.performacedashboardapi.dto.ChartDataset;
import com.performacedashboardapi.performacedashboardapi.dto.TraceResponseSummary;
import com.performacedashboardapi.performacedashboardapi.service.PerformanceDashboardService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(  "/api/performanceDashboard")
@AllArgsConstructor
@CrossOrigin(originPatterns = "*")
public class PerformanceDashboardController {

    private PerformanceDashboardService performanceDashboardService;

    @GetMapping("/data")
    @ResponseBody
    public List<TraceResponseSummary> getTraceResponseSummaries(@RequestParam(name = "traceIds") String... traceIds) throws JsonProcessingException {
        return performanceDashboardService.getTraceResponseSummaries(traceIds);
    }

    @GetMapping("/data/chart")
    @ResponseBody
    public List<ChartDataset> getChartDatasets(@RequestParam(name = "traceIds") String... traceIds) throws JsonProcessingException {
        return performanceDashboardService.getTraceChartDatasets(
                performanceDashboardService.getTraceResponseSummaries(traceIds)
        );
    }
}
