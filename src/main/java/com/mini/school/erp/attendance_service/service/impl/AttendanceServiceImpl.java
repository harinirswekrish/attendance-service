package com.mini.school.erp.attendance_service.service.impl;

import com.mini.school.erp.attendance_service.client.CourseClient;
import com.mini.school.erp.attendance_service.client.StudentClient;
import com.mini.school.erp.attendance_service.dto.AttendanceMarkRequest;
import com.mini.school.erp.attendance_service.dto.AttendanceResponse;
import com.mini.school.erp.attendance_service.entity.Attendance;
import com.mini.school.erp.attendance_service.exception.errormessages.ErrorMessages;
import com.mini.school.erp.attendance_service.repository.AttendanceRepository;
import com.mini.school.erp.attendance_service.service.AttendanceService;
import com.mini.school.erp.attendance_service.validation.business.BusinessValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AttendanceServiceImpl implements AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final BusinessValidation businessValidation;

    private final StudentClient studentClient;
    private final CourseClient courseClient;

    @Override
    public AttendanceResponse markAttendance(AttendanceMarkRequest request) {
        businessValidation.validateAttendanceMarking(request);

        String student = studentClient.getStudent(request.getStudentId());
        if (student == null) {
            throw new RuntimeException(ErrorMessages.STUDENT_NOT_FOUND + request.getStudentId());
        }

        String course = courseClient.getCourse(request.getCourseId());
        if (course == null) {
            throw new RuntimeException(ErrorMessages.COURSE_NOT_FOUND + request.getCourseId());
        }

        // Save attendance
        Attendance attendance = Attendance.builder()
                .studentId(request.getStudentId())
                .courseId(request.getCourseId())
                .attendanceDate(request.getAttendanceDate())
                .status(request.getStatus())
                .build();

        Attendance saved = attendanceRepository.save(attendance);

        return AttendanceResponse.builder()
                .id(saved.getId())
                .studentId(saved.getStudentId())
                .courseId(saved.getCourseId())
                .attendanceDate(saved.getAttendanceDate())
                .status(saved.getStatus())
                .createdAt(saved.getCreatedAt())
                .build();
    }

    @Override
    public List<AttendanceResponse> getAttendanceByCourseAndDate(Long courseId, LocalDate date) {
        return attendanceRepository.findByCourseIdAndAttendanceDate(courseId, date)
                .stream()
                .map(a -> AttendanceResponse.builder()
                        .id(a.getId())
                        .studentId(a.getStudentId())
                        .courseId(a.getCourseId())
                        .attendanceDate(a.getAttendanceDate())
                        .status(a.getStatus())
                        .createdAt(a.getCreatedAt())
                        .build())
                .toList();
    }
}
