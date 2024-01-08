package com.greathealth.greathealth.config;

import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


@Configuration
public class RestTemplateConfig {


    private static final int HTTP_MAX_IDLE = 20;
    private static final int HTTP_KEEP_ALIVE = 20;
    private static final int HTTP_CONNECTION_TIMEOUT = 30;

    @Bean
    public RestTemplate restTemplate() {
        ConnectionPool pool = new ConnectionPool(HTTP_MAX_IDLE, HTTP_KEEP_ALIVE, TimeUnit.SECONDS);
        OkHttpClient builder = new OkHttpClient.Builder()
                .retryOnConnectionFailure(false)
                .connectionPool(pool)
                .connectTimeout(HTTP_CONNECTION_TIMEOUT, TimeUnit.SECONDS)
                .build();


        BufferingClientHttpRequestFactory bufferingClientHttpRequestFactory = new BufferingClientHttpRequestFactory(new OkHttp3ClientHttpRequestFactory(builder));

        RestTemplate restTemplate = new RestTemplate(bufferingClientHttpRequestFactory);

        List<ClientHttpRequestInterceptor> interceptors = restTemplate.getInterceptors();
        if (CollectionUtils.isEmpty(interceptors)) {
            interceptors = new ArrayList<>();
        }
        interceptors.add(new LoggingInterceptor());
        restTemplate.setInterceptors(interceptors);

        return restTemplate;
    }
}
