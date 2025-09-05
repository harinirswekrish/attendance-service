package com.mini.school.erp.attendance_service.service;

import com.mini.school.erp.attendance_service.dto.AttendanceMarkRequest;
import com.mini.school.erp.attendance_service.dto.AttendanceResponse;
import java.time.LocalDate;
import java.util.List;

public interface AttendanceService {
    AttendanceResponse markAttendance(AttendanceMarkRequest request);
    List<AttendanceResponse> getAttendanceByCourseAndDate(Long courseId, LocalDate date);
}