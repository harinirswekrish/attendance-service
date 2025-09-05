package com.mini.school.erp.attendance_service.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
}
