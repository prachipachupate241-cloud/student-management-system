package com.student.management.controller;

import com.student.management.service.StudentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
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
            Authentication authentication,
            Model model
    ) {

        // ✅ USER ROLE

        boolean isUser =
                authentication.getAuthorities()
                        .stream()
                        .anyMatch(a ->
                                a.getAuthority()
                                        .equals("ROLE_USER"));

        // ✅ USER → DIRECT RESULTS PAGE

        if (isUser) {

            return "redirect:/results";
        }

        // ✅ ADMIN → RESULT MANAGEMENT PAGE

        model.addAttribute(
                "students",
                studentService.getAllStudents()
        );

        return "results-system";
    }
}