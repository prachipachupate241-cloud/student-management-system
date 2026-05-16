package com.student.management.service;

import com.student.management.entity.Result;

import com.student.management.repository.ResultRepository;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResultService {

    @Autowired
    private ResultRepository repository;

    // ✅ SAVE RESULT

    public void saveResult(
            Result result
    ) {

        // TOTAL

        int total =
                result.getJavaMarks()
                        +
                        result.getPythonMarks()
                        +
                        result.getDbmsMarks()
                        +
                        result.getCloudMarks();

        result.setTotal(total);

        // PERCENTAGE

        double percentage =
                (total / 400.0) * 100;

        result.setPercentage(
                percentage
        );

        // GRADE

        if (percentage >= 90) {

            result.setGrade("A+");

            result.setResult("Pass");
        }

        else if (percentage >= 75) {

            result.setGrade("A");

            result.setResult("Pass");
        }

        else if (percentage >= 60) {

            result.setGrade("B");

            result.setResult("Pass");
        }

        else if (percentage >= 40) {

            result.setGrade("C");

            result.setResult("Pass");
        }

        else {

            result.setGrade("F");

            result.setResult("Fail");
        }

        repository.save(result);
    }

    // ✅ GET ALL RESULTS

    public List<Result> getAllResults() {

        return repository.findAll();
    }

    // ✅ DELETE RESULT

    public void deleteResult(
            Long id
    ) {

        repository.deleteById(id);
    }
}
