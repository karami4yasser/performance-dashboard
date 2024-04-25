package com.performacedashboardapi.performacedashboardapi.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TraceResponseSummary {

    private String traceId;
    private List<Span> spans;
    private float duration;
}
