package com.student.management.controller;

import com.student.management.entity.Result;
import com.student.management.entity.student;

import com.student.management.service.ResultService;
import com.student.management.service.StudentService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.*;

@Controller
public class ResultController {

    @Autowired
    private ResultService resultService;

    @Autowired
    private StudentService studentService;

    // ✅ RESULT PAGE

    @GetMapping("/results")
    public String resultPage(
            Model model
    ) {

        model.addAttribute(
                "results",
                resultService.getAllResults()
        );

        return "results";
    }

    // ✅ ADD RESULT PAGE (ADMIN ONLY)

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/add-result/{id}")
    public String addResultPage(

            @PathVariable Long id,

            Model model
    ) {

        student student =
                studentService.getById(id);

        Result result =
                new Result();

        result.setStudentId(
                student.getId()
        );

        result.setStudentName(
                student.getName()
        );

        model.addAttribute(
                "result",
                result
        );

        return "add-result";
    }

    // ✅ SAVE RESULT (ADMIN ONLY)

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/saveResult")
    public String saveResult(

            @ModelAttribute Result result
    ) {

        resultService.saveResult(
                result
        );

        return "redirect:/results";
    }

    // ✅ DELETE RESULT (ADMIN ONLY)

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/deleteResult/{id}")
    public String deleteResult(

            @PathVariable Long id
    ) {

        resultService.deleteResult(id);

        return "redirect:/results";
    }
}