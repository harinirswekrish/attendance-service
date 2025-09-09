package com.mini.school.erp.attendance_service.client;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class StudentClient {

    @Value("${service.student.url}")
    private String studentServiceUrl;

    private final RestTemplate restTemplate;


    public String getStudent(Long studentId) {
        ResponseEntity<String> response = restTemplate.exchange(
                studentServiceUrl + "/" + studentId,
                HttpMethod.GET,
                null,
                String.class
        );
        return response.getBody();
    }
}
