package com.mini.school.erp.attendance_service.client;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class CourseClient {

    @Value("${service.course.url}")
    private String courseServiceUrl;


    private final RestTemplate restTemplate;
    private final String courseService = courseServiceUrl;

    public String getCourse(Long courseId) {
        ResponseEntity<String> response = restTemplate.exchange(
                courseService + "/" + courseId,
                HttpMethod.GET,
                null,
                String.class
        );
        return response.getBody();
    }
}
