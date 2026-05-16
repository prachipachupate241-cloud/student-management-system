package com.student.management.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    // ✅ LOGIN PAGE

    @GetMapping("/login")
    public String login() {

        return "login";
    }
}