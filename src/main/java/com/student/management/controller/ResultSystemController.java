package com.student.management.controller;

import com.student.management.entity.student;

import com.student.management.service.StudentService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ResultSystemController {

    @Autowired
    private StudentService studentService;

    // ✅ RESULT SYSTEM PAGE

    @GetMapping("/results-system")
    public String resultSystemPage(
            Model model
    ) {

        model.addAttribute(
                "students",
                studentService.getAllStudents()
        );

        return "results-system";
    }
}