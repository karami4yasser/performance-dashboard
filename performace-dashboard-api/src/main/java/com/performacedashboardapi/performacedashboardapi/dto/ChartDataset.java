package com.performacedashboardapi.performacedashboardapi.dto;

import lombok.Data;

import java.util.List;

@Data
public class ChartDataset {


    private String traceId;
    private List<String> labels;
    private List<Float> durations;
    private List<String> backgroundColor;
    private List<String> borderColor;
    private Float totalDuration;
}
