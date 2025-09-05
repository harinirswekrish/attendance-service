package com.mini.school.erp.attendance_service.dto;

import com.mini.school.erp.attendance_service.entity.AttendanceStatus;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AttendanceMarkRequest {
    @NotNull(message = "studentId is required")
    private Long studentId;

    @NotNull(message = "courseId is required")
    private Long courseId;

    @NotNull
    private LocalDate attendanceDate;

    private AttendanceStatus status;
}
