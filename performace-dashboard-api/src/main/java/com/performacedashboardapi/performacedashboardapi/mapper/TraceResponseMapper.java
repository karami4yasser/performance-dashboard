package com.performacedashboardapi.performacedashboardapi.mapper;

import com.fasterxml.jackson.databind.JsonNode;
import com.performacedashboardapi.performacedashboardapi.dto.ChartDataset;
import com.performacedashboardapi.performacedashboardapi.dto.Span;
import com.performacedashboardapi.performacedashboardapi.dto.TraceResponseSummary;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TraceResponseMapper {
    public TraceResponseSummary jsonNodeToTraceResponseSummary(JsonNode jsonNode) {

        TraceResponseSummary traceResponseSummary = new TraceResponseSummary();
        JsonNode data = jsonNode.get("data").get(0);
        traceResponseSummary.setTraceId(data.get("traceID").asText());
        List<Span> spans = new ArrayList<>();
        int totalDuration = 0;
        for(JsonNode spanJsonNode: data.get("spans")){
            Span span = new Span();
            span.setSpanId(spanJsonNode.get("spanID").asText());
            span.setService(data.get("processes").get(spanJsonNode.get("processID").asText()).get("serviceName").asText());
            span.setOperation(spanJsonNode.get("operationName").asText());
            span.setDuration((float) spanJsonNode.get("duration").asInt() /1000);
            span.setScope(
                    hasTag(spanJsonNode,"otel.scope.name") ? getTagValue(spanJsonNode,"otel.scope.name") : "Unkown Scope"
            );

            if(hasTag(spanJsonNode,"sql.query")) {
                span.setSqlQuery(
                        getTagValue(spanJsonNode,"sql.query")
                );
            }
            if(hasTag(spanJsonNode,"http.url")) {
                span.setHttpUrl(
                        getTagValue(spanJsonNode,"http.url")
                );
            }
            totalDuration+=spanJsonNode.get("duration").asInt();
            spans.add(span);
        }
        traceResponseSummary.setSpans(spans);
        traceResponseSummary.setDuration(totalDuration);
        return traceResponseSummary;
    }



    public ChartDataset TraceResponseSummaryToChartDataset(TraceResponseSummary traceResponseSummary){
        ChartDataset chartDataset = new ChartDataset();
        Map<String,Float> tempChartDataset = new HashMap<>();
        for (Span span : traceResponseSummary.getSpans()){
            float duration = tempChartDataset.getOrDefault(span.getOperation(),0.00F);
            duration+=span.getDuration();
            String label = span.getOperation();
            if (Objects.nonNull(span.getSqlQuery())) {
                label = "db-query : "+ span.getSqlQuery();
            }
            /*if (Objects.nonNull(span.getHttpUrl())) {
                label = "http-request : "+ span.getHttpUrl();
            }*/
            tempChartDataset.put(label, duration);
        }
        List<String> labels = tempChartDataset.keySet().stream().toList();
        List<Float> durations = labels.stream().map(tempChartDataset::get).toList();
        List<String> backgroundColor = new ArrayList<>();
        List<String> borderColor = new ArrayList<>();
        for(String label : labels){
            String color = String.format("rgba(%s, %s, %s, %s)",0 + Math.random() * (255),0 + Math.random() * (255),0 + Math.random() * (255),0 + Math.random() * (255));
            backgroundColor.add(color);
            borderColor.add(color);
        }
        chartDataset.setDurations(durations);
        chartDataset.setLabels(labels);
        chartDataset.setBackgroundColor(backgroundColor);
        chartDataset.setBorderColor(borderColor);
        chartDataset.setTraceId(traceResponseSummary.getTraceId());
        chartDataset.setTotalDuration(
                durations.stream().reduce(0.00F, Float::sum)
        );

        return chartDataset;
    }


    private String getTagValue(JsonNode spanJsonNode,String tagName) {

        for(JsonNode tagJsonNode : spanJsonNode.get("tags")){
            if(tagName.equals(tagJsonNode.get("key").asText())) {
                return tagJsonNode.get("value").asText();
            }
        }
        return null;
    }

    private Boolean hasTag(JsonNode spanJsonNode,String tagName) {

        for(JsonNode tagJsonNode : spanJsonNode.get("tags")){
            if(tagName.equals(tagJsonNode.get("key").asText())) {
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }
}
