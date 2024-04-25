package com.performacedashboardapi.performacedashboardapi.restClient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Service
public class RestClient {

    private final RestTemplate restTemplate;
    @Value("${jaeger.host}")
    private String jaegerEndpoint;
    @Autowired
    public RestClient(RestTemplateBuilder builder) {
        this.restTemplate = builder
                .setConnectTimeout(Duration.ofMillis(15000))
                .build();
    }

    public JsonNode getJaegerTraceDetails(String traceId) throws JsonProcessingException {
        String url = String.format(jaegerEndpoint+"/%s",traceId);
        ObjectMapper mapper = new ObjectMapper();
        String json = restTemplate.getForObject(url, String.class);
        mapper.readTree(json);
        return mapper.readTree(json);
    }

}
