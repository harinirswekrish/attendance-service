package com.mini.school.erp.attendance_service.controller;


import com.mini.school.erp.attendance_service.dto.AttendanceMarkRequest;
import com.mini.school.erp.attendance_service.dto.AttendanceResponse;
import com.mini.school.erp.attendance_service.service.AttendanceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/attendances")
@RequiredArgsConstructor
public class AttendanceController {

    private final AttendanceService attendanceService;

    @PostMapping
    @PreAuthorize("hasAnyRole('STAFF')")
    public ResponseEntity<AttendanceResponse> markAttendance(@RequestBody @Valid AttendanceMarkRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(attendanceService.markAttendance(request));
    }

    @GetMapping("/course/{courseId}")
    @PreAuthorize("hasAnyRole('ADMIN','STAFF')")
    public ResponseEntity<List<AttendanceResponse>> getAttendanceByCourseAndDate(
            @PathVariable Long courseId,
            @RequestParam LocalDate date) {
        return ResponseEntity.ok(attendanceService.getAttendanceByCourseAndDate(courseId, date));
    }
}

