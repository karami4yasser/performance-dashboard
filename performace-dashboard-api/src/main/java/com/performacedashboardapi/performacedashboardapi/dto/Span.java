package com.performacedashboardapi.performacedashboardapi.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDate;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Span {
    private String spanId;
    private String scope;
    private String service;
    private String Operation;
    private float duration;
    private String sqlQuery;
    private String httpUrl;
}
