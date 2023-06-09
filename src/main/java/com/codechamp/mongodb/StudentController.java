package com.codechamp.mongodb;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/students")
@RequiredArgsConstructor
@AllArgsConstructor
public class StudentController {

    private final StudentService studentService;
    @GetMapping
    public List<Student> fetchAllStudents(){
        return studentService.getAllStudents();

    }
}
