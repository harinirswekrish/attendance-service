package com.mini.school.erp.attendance_service.validation.business;

import com.mini.school.erp.attendance_service.dto.AttendanceMarkRequest;
import com.mini.school.erp.attendance_service.dto.CourseResponse;
import com.mini.school.erp.attendance_service.dto.EnrollmentResponse;
import com.mini.school.erp.attendance_service.dto.StudentResponse;
import com.mini.school.erp.attendance_service.exception.BusinessValidationException;
import com.mini.school.erp.attendance_service.exception.errormessages.ErrorMessages;
import com.mini.school.erp.attendance_service.repository.AttendanceRepository;
import com.mini.school.erp.attendance_service.utils.AppConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class BusinessValidation {

    private final AttendanceRepository attendanceRepository;
    private final RestTemplate restTemplate;

    @Value("${service.student.url}")
    private String studentServiceUrl;

    @Value("${service.course.url}")
    private String courseServiceUrl;

    @Value("${service.enrollment.url}")
    private String enrollmentServiceUrl;

    /**
     * Validates business rules before marking attendance
     */
    public void validateAttendanceMarking(AttendanceMarkRequest request) {

        // Check if student exists
        StudentResponse studentExists = restTemplate.getForObject(
                studentServiceUrl + "/" + request.getStudentId(),
                StudentResponse.class
        );
        if (studentExists == null) {
            throw new BusinessValidationException(ErrorMessages.STUDENT_NOT_FOUND + request.getStudentId());
        }

        // Check if course exists
        CourseResponse courseExists = restTemplate.getForObject(
                courseServiceUrl + "/" + request.getCourseId(),
                CourseResponse.class
        );
        if (courseExists == null) {
            throw new BusinessValidationException(ErrorMessages.COURSE_NOT_FOUND + request.getCourseId());
        }

        // Check if student is enrolled in the course
        EnrollmentResponse[] enrollments = restTemplate.getForObject(
                enrollmentServiceUrl + "/" + AppConstants.STUDENT + "/" + request.getStudentId(),
                EnrollmentResponse[].class
        );

        boolean enrolled = enrollments != null && Arrays.stream(enrollments)
                .anyMatch(e -> e.getCourseId().equals(request.getCourseId()) && AppConstants.ACTIVE.equals(e.getStatus()));

        if (!enrolled) {
            throw new BusinessValidationException(ErrorMessages.STUDENT_NOT_ENROLLED);
        }

        // Check if attendance already exists
        boolean alreadyExists = attendanceRepository.existsByStudentIdAndCourseIdAndAttendanceDate(
                request.getStudentId(),
                request.getCourseId(),
                request.getAttendanceDate()
        );
        if (alreadyExists) {
            throw new BusinessValidationException(ErrorMessages.ATTENDANCE_VALIDATE);
        }
    }
}