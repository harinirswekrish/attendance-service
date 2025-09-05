package com.mini.school.erp.attendance_service.dto;

import com.mini.school.erp.attendance_service.entity.AttendanceStatus;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AttendanceResponse {

    private Long id;
    private Long studentId;
    private Long courseId;
    private LocalDate attendanceDate;
    private AttendanceStatus status;
    private LocalDateTime createdAt;
}
