package com.performacedashboardapi.performacedashboardapi.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.performacedashboardapi.performacedashboardapi.dto.ChartDataset;
import com.performacedashboardapi.performacedashboardapi.dto.TraceResponseSummary;
import com.performacedashboardapi.performacedashboardapi.mapper.TraceResponseMapper;
import com.performacedashboardapi.performacedashboardapi.restClient.RestClient;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class PerformanceDashboardService {
    private RestClient restClient;
    private TraceResponseMapper traceResponseMapper;

    public TraceResponseSummary getTraceResponseSummary(String traceId) throws JsonProcessingException {
        JsonNode jsonNode = restClient.getJaegerTraceDetails(traceId);
         return traceResponseMapper.jsonNodeToTraceResponseSummary(jsonNode);
    }

    public List<TraceResponseSummary> getTraceResponseSummaries(String... traceIds) throws JsonProcessingException {
        List<TraceResponseSummary> traceResponseSummaries = new ArrayList<>();

        for(String traceId : traceIds) {
            traceResponseSummaries.add(getTraceResponseSummary(traceId));
        }
        return traceResponseSummaries;
    }

    public ChartDataset getTraceChartDataset(TraceResponseSummary traceResponseSummary) {
        return traceResponseMapper.TraceResponseSummaryToChartDataset(traceResponseSummary);
    }

    public List<ChartDataset> getTraceChartDatasets(List<TraceResponseSummary> traceResponseSummaries) {
        List<ChartDataset> chartDatasets = new ArrayList<>();

        for(TraceResponseSummary traceResponseSummary : traceResponseSummaries) {
            chartDatasets.add(getTraceChartDataset(traceResponseSummary));
        }
        return chartDatasets;
    }
}
