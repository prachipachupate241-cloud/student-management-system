package com.student.management.controller;

import com.student.management.entity.student;
import com.student.management.service.StudentService;
import com.student.management.service.AttendanceService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class StudentController {

    @Autowired
    private StudentService service;

    @Autowired
    private AttendanceService attendanceService;

    // 📋 STUDENT LIST + SEARCH + ATTENDANCE %
    @GetMapping("/students")
    public String list(

            @RequestParam(
                    value = "keyword",
                    required = false
            )

            String keyword,

            Model model
    ) {

        List<student> students;

        // 🔍 SEARCH
        if (keyword != null &&
                !keyword.isEmpty()) {

            students =
                    service.searchStudents(keyword);

        } else {

            students =
                    service.getAllStudents();
        }

        // 📋 STUDENTS
        model.addAttribute(
                "students",
                students
        );

        // 📊 DASHBOARD
        model.addAttribute(
                "totalStudents",
                service.totalStudents()
        );

        model.addAttribute(
                "itCount",
                service.totalITStudents()
        );

        model.addAttribute(
                "bcaCount",
                service.totalBCAStudents()
        );

        model.addAttribute(
                "bcomCount",
                service.totalBCOMStudents()
        );

        // 🔍 SEARCH VALUE
        model.addAttribute(
                "keyword",
                keyword
        );

        // 📈 ATTENDANCE %
        Map<Long, Double> attendanceMap =
                new HashMap<>();

        for (student s : students) {

            double percentage =
                    attendanceService
                            .calculateAttendance(
                                    s.getId()
                            );

            attendanceMap.put(
                    s.getId(),
                    percentage
            );
        }

        model.addAttribute(
                "attendanceMap",
                attendanceMap
        );

        return "student-list";
    }

    // ➕ ADD STUDENT FORM
    @GetMapping("/add")
    public String addForm(Model model) {

        model.addAttribute(
                "student",
                new student()
        );

        return "add-student";
    }


// 💾 SAVE STUDENT

    @PostMapping("/save")
    public String save(
            @ModelAttribute student student
    ) {

        // ✅ AUTO STUDENT NUMBER

        String studentNo =
                service.generateStudentNo(
                        student.getCourse()
                );

        student.setStudentNo(studentNo);

        service.saveStudent(student);

        return "redirect:/students";
    }


    // ✏ EDIT STUDENT
    @GetMapping("/edit/{id}")
    public String edit(

            @PathVariable Long id,

            Model model
    ) {

        model.addAttribute(
                "student",
                service.getById(id)
        );

        return "add-student";
    }

    // ❌ DELETE STUDENT
    @GetMapping("/delete/{id}")
    public String delete(
            @PathVariable Long id
    ) {

        service.deleteById(id);

        return "redirect:/students";
    }
}