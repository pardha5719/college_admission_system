package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Student;
import com.example.demo.service.StudentService;

@RestController
@RequestMapping("/students")
public class StudentController {
	
	@Autowired
	private StudentService service;
	
    @PostMapping("/register")
    public Student register(@RequestBody Student s) { return service.registerStudent(s); }

    @PostMapping("/{id}/apply/{courseId}")
    public ResponseEntity<?> apply(@PathVariable Long id, @PathVariable Long courseId) {
        return ResponseEntity.ok(service.applyForCourse(id, courseId));
    }

    @GetMapping
    public List<Student> getAll() { return service.getAllStudents(); }
}
