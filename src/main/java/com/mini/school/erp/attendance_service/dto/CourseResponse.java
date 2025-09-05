package com.mini.school.erp.attendance_service.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseResponse {
    private Long id;
    private String courseName;
    private String description;
}