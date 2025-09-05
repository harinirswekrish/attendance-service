package com.mini.school.erp.attendance_service.repository;

import com.mini.school.erp.attendance_service.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    boolean existsByStudentIdAndCourseIdAndAttendanceDate(Long studentId, Long courseId, LocalDate attendanceDate);

    List<Attendance> findByCourseIdAndAttendanceDate(Long courseId, LocalDate attendanceDate);

}
