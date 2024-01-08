package com.greathealth.greathealth.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

@Slf4j
public class LoggingInterceptor implements ClientHttpRequestInterceptor {


    @Override
    public ClientHttpResponse intercept(HttpRequest req, byte[] reqBody, ClientHttpRequestExecution ex) throws IOException {

        log.info("=========================== request begin ===========================");
        log.info("uri : {}", req.getURI());
        log.info("method : {}", req.getMethod());
        log.info("headers : {}", req.getHeaders());
        log.info("Request body: {}", new String(reqBody, StandardCharsets.UTF_8));
        log.info("============================ request end ============================");

        ClientHttpResponse response = ex.execute(req, reqBody);
        InputStreamReader isr = new InputStreamReader(response.getBody(), StandardCharsets.UTF_8);


        log.info("============================ response begin ============================");
        log.info("Status code  : {}", response.getStatusCode());
        log.info("Status text  : {}", response.getStatusText());
        log.info("Headers      : {}", response.getHeaders());
        String body = new BufferedReader(isr).lines().collect(Collectors.joining("\n"));
        log.info("Response body: {}", body);
        log.info("============================= response end =============================");
        return response;
    }
}
