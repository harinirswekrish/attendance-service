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
    private final String studentService = studentServiceUrl;

    public String getStudent(Long studentId) {
        ResponseEntity<String> response = restTemplate.exchange(
                studentService + "/" + studentId,
                HttpMethod.GET,
                null,
                String.class
        );
        return response.getBody();
    }
}
