package com.mini.school.erp.attendance_service.client;

import com.mini.school.erp.attendance_service.exception.BusinessValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class CourseClient {

    @Value("${service.course.url}")
    private String courseServiceUrl;

    private final RestTemplate restTemplate;

    public String getCourse(Long courseId) {
        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    courseServiceUrl + "/" + courseId,
                    HttpMethod.GET,
                    null,
                    String.class
            );
            return response.getBody();
        } catch (HttpClientErrorException.NotFound ex) {
            throw new BusinessValidationException("Course not found with ID: " + courseId);
        } catch (HttpClientErrorException.BadRequest ex) {
            throw new BusinessValidationException("Invalid course request for ID: " + courseId);
        } catch (HttpClientErrorException.Forbidden ex) {
            throw new BusinessValidationException("Access denied when fetching course ID: " + courseId);
        } catch (HttpClientErrorException ex) {
            throw new BusinessValidationException("Unexpected error from course-service: " + ex.getStatusCode());
        }
    }
}
