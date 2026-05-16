package com.student.management.repository;

import com.student.management.entity.Attendance;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface AttendanceRepository
        extends JpaRepository<Attendance, Long> {

    // ✅ CHECK SAME DATE ATTENDANCE

    Optional<Attendance>
    findByStudentIdAndDate(
            Long studentId,
            LocalDate date
    );
}