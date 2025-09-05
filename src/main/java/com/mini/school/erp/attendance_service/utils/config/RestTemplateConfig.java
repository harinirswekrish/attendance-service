package com.mini.school.erp.attendance_service.utils.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Configuration
public class RestTemplateConfig {

    private final HttpServletRequest request;

    public RestTemplateConfig(HttpServletRequest request) {
        this.request = request;
    }

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setInterceptors(Collections.singletonList((ClientHttpRequestInterceptor) (req, body, execution) -> {
            String token = request.getHeader("Authorization");
            if (token != null) {
                req.getHeaders().set("Authorization", token);
            }
            return execution.execute(req, body);
        }));
        return restTemplate;
    }
}
