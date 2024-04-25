package com.performacedashboardapi.performacedashboardapi.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.performacedashboardapi.performacedashboardapi.dto.ChartDataset;
import com.performacedashboardapi.performacedashboardapi.dto.TraceResponseSummary;
import com.performacedashboardapi.performacedashboardapi.service.PerformanceDashboardService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TraceResponseMapperTest {

    @Autowired
    private TraceResponseMapper traceResponseMapper;

     private static final String CLASSPATH = "/jaeger";

    @Test
    public void getTraceResponseSummaryTest() throws IOException {

        InputStream in=TraceResponseMapperTest.class.getResourceAsStream(CLASSPATH + "/response.json");
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readValue(in, JsonNode.class);
        TraceResponseSummary traceResponseSummary = traceResponseMapper.jsonNodeToTraceResponseSummary(
               jsonNode
        );
        System.out.println(traceResponseSummary);
    }

    @Test
    public void getChartDatasetTest() throws IOException {

        InputStream in=TraceResponseMapperTest.class.getResourceAsStream(CLASSPATH + "/response.json");
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readValue(in, JsonNode.class);
        TraceResponseSummary traceResponseSummary = traceResponseMapper.jsonNodeToTraceResponseSummary(
                jsonNode
        );

        ChartDataset chartDataset = traceResponseMapper.TraceResponseSummaryToChartDataset(traceResponseSummary);
        System.out.println(chartDataset);
    }
}
