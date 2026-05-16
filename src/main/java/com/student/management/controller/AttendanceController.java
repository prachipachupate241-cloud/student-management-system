package com.student.management.controller;

import com.student.management.entity.Attendance;
import com.student.management.entity.student;

import com.student.management.service.AttendanceService;
import com.student.management.service.StudentService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.YearMonth;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    @Autowired
    private StudentService studentService;

    // 📋 ATTENDANCE PAGE

    @GetMapping("/attendance")
    public String attendancePage(

            @RequestParam(
                    required = false
            )

            String course,

            @RequestParam(
                    required = false
            )

            String year,

            Model model
    ) {

        List<student> students;

        // ✅ FILTER

        if (course != null &&
                year != null &&
                !course.isEmpty() &&
                !year.isEmpty()) {

            students =
                    studentService
                            .filterStudents(
                                    course,
                                    year
                            );
        }

        else {

            students =
                    studentService.getAllStudents();
        }

        model.addAttribute(
                "students",
                students
        );

        model.addAttribute(
                "course",
                course
        );

        model.addAttribute(
                "year",
                year
        );

        return "attendance";
    }

    // 💾 SAVE ATTENDANCE

    @PostMapping("/saveAttendance")
    public String saveAttendance(

            @RequestParam Long studentId,

            @RequestParam String studentName,

            @RequestParam String status,

            @RequestParam String date
    ) {

        Attendance attendance =
                new Attendance();

        attendance.setStudentId(studentId);

        attendance.setStudentName(studentName);

        attendance.setStatus(status);

        attendance.setDate(
                LocalDate.parse(date)
        );

        boolean saved =
                attendanceService.saveAttendance(
                        attendance
                );

        // ✅ ALREADY MARKED

        if (!saved) {

            return "redirect:/attendance?error";
        }

        return "redirect:/attendance?success";
    }

    // 📜 ATTENDANCE HISTORY

    @GetMapping("/attendance-history")
    public String attendanceHistory(Model model) {

        model.addAttribute(
                "attendanceList",
                attendanceService.getAllAttendance()
        );

        return "attendance-history";
    }

    // ❌ DELETE ATTENDANCE

    @GetMapping("/deleteAttendance/{id}")
    public String deleteAttendance(
            @PathVariable Long id
    ) {

        attendanceService.deleteAttendance(id);

        return "redirect:/attendance-history";
    }

    // 📊 MONTHLY ATTENDANCE REPORT

    @GetMapping("/monthly-attendance")
    public String monthlyAttendance(Model model) {

        List<student> students =
                studentService.getAllStudents();

        Map<Long, Long> presentMap =
                new HashMap<>();

        Map<Long, Long> absentMap =
                new HashMap<>();

        Map<Long, Double> percentageMap =
                new HashMap<>();

        // 📅 CURRENT MONTH

        YearMonth currentMonth =
                YearMonth.now();

        int totalDays =
                currentMonth.lengthOfMonth();

        // 🔄 LOOP THROUGH STUDENTS

        for (student s : students) {

            long present =
                    attendanceService.presentCount(
                            s.getId()
                    );

            long absent =
                    totalDays - present;

            double percentage =
                    ((double) present / totalDays) * 100;

            presentMap.put(
                    s.getId(),
                    present
            );

            absentMap.put(
                    s.getId(),
                    absent
            );

            percentageMap.put(
                    s.getId(),
                    percentage
            );
        }

        model.addAttribute(
                "students",
                students
        );

        model.addAttribute(
                "presentMap",
                presentMap
        );

        model.addAttribute(
                "absentMap",
                absentMap
        );

        model.addAttribute(
                "percentageMap",
                percentageMap
        );

        return "monthly-attendance";
    }

    // 📊 STUDENT ATTENDANCE DETAILS

    @GetMapping("/student-attendance/{id}")
    public String studentAttendance(

            @PathVariable Long id,

            Model model
    ) {

        student student =
                studentService.getById(id);

        List<Attendance> attendanceList =
                attendanceService
                        .getAllAttendance()

                        .stream()

                        .filter(a ->
                                a.getStudentId().equals(id)
                        )

                        .toList();

        long present =
                attendanceList

                        .stream()

                        .filter(a ->
                                a.getStatus()
                                        .equalsIgnoreCase("Present")
                        )

                        .count();

        long total =
                attendanceList.size();

        long absent =
                total - present;

        double percentage = 0;

        if (total > 0) {

            percentage =
                    ((double) present / total) * 100;
        }

        model.addAttribute(
                "student",
                student
        );

        model.addAttribute(
                "attendanceList",
                attendanceList
        );

        model.addAttribute(
                "present",
                present
        );

        model.addAttribute(
                "absent",
                absent
        );

        model.addAttribute(
                "percentage",
                percentage
        );

        return "student-attendance";
    }
}