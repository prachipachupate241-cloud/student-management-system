package com.student.management.service;

import com.student.management.entity.Attendance;
import com.student.management.repository.AttendanceRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class AttendanceService {

    @Autowired
    private AttendanceRepository repository;

    // 💾 SAVE ATTENDANCE ONLY ONCE

    public boolean saveAttendance(
            Attendance attendance
    ) {

        Optional<Attendance> existing =
                repository.findByStudentIdAndDate(
                        attendance.getStudentId(),
                        attendance.getDate()
                );

        // ✅ ALREADY EXISTS

        if (existing.isPresent()) {

            return false;
        }

        repository.save(attendance);

        return true;
    }

    // 📋 GET ALL ATTENDANCE

    public List<Attendance> getAllAttendance() {

        return repository.findAll();
    }

    // ❌ DELETE ATTENDANCE

    public void deleteAttendance(Long id) {

        repository.deleteById(id);
    }

    // 📊 CALCULATE ATTENDANCE %

    public double calculateAttendance(
            Long studentId
    ) {

        long total =
                repository.findAll()

                        .stream()

                        .filter(a ->
                                a.getStudentId().equals(studentId)
                        )

                        .count();

        long present =
                repository.findAll()

                        .stream()

                        .filter(a ->
                                a.getStudentId().equals(studentId)
                                        &&
                                        a.getStatus()
                                                .equalsIgnoreCase("Present")
                        )

                        .count();

        if (total == 0) {

            return 0;
        }

        return ((double) present / total) * 100;
    }

    // ✅ PRESENT COUNT

    public long presentCount(
            Long studentId
    ) {

        return repository.findAll()

                .stream()

                .filter(a ->
                        a.getStudentId().equals(studentId)
                                &&
                                a.getStatus()
                                        .equalsIgnoreCase("Present")
                )

                .count();
    }

    // 📅 TODAY CHECK

    public boolean alreadyMarked(
            Long studentId,
            LocalDate date
    ) {

        Optional<Attendance> attendance =
                repository.findByStudentIdAndDate(
                        studentId,
                        date
                );

        return attendance.isPresent();
    }
}