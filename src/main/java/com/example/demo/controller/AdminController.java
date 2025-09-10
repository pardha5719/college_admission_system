//package com.example.demo.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import com.example.demo.model.Course;
//import com.example.demo.service.AdminService;
//import com.example.demo.repository.CourseRepository;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/admin") // ✅ Changed base path to avoid overlap with view controller
//public class AdminController {
//
//    @Autowired
//    private AdminService adminService;
//
//    @Autowired
//    private CourseRepository courseRepo;
//
//    // Evaluate applications
//    @PostMapping("/evaluate")
//    public ResponseEntity<String> evaluateApplications() {
//        adminService.evaluateApplications();
//        return ResponseEntity.ok("Applications evaluated");
//    }
//
//    // Add a new course
//    @PostMapping("/course")
//    public ResponseEntity<Course> addCourse(@RequestBody Course course) {
//        return ResponseEntity.ok(courseRepo.save(course));
//    }
//
//    // Get all courses (JSON)
//    @GetMapping("/courses")
//    public List<Course> getCourses() {
//        return courseRepo.findAll();
//    }
//    
//    
//}
package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.Course;
import com.example.demo.model.Student;
import com.example.demo.model.Application;
import com.example.demo.repository.CourseRepository;
import com.example.demo.repository.StudentRepository;
import com.example.demo.repository.ApplicationRepository;
import com.example.demo.service.AdminService;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private CourseRepository courseRepo;

    @Autowired
    private StudentRepository studentRepo;

    @Autowired
    private ApplicationRepository applicationRepo;

    // Submit a new application
    @PostMapping("/apply")
    public ResponseEntity<String> submitApplication(@RequestParam Long studentId,
                                                     @RequestParam Long courseId) {
        Student student = studentRepo.findById(studentId).orElse(null);
        Course course = courseRepo.findById(courseId).orElse(null);

        if (student == null || course == null) {
            return ResponseEntity.badRequest().body("Invalid student or course ID.");
        }

        Application app = new Application();
        app.setStudent(student);
        app.setCourse(course);
        app.setStatus("Pending");

        applicationRepo.save(app);
        return ResponseEntity.ok("Application submitted successfully.");
    }

    // Evaluate applications
    @PostMapping("/evaluate")
    public ResponseEntity<String> evaluateApplications() {
        adminService.evaluateApplications();
        return ResponseEntity.ok("Applications evaluated");
    }

    // Add a new course
    @PostMapping("/course")
    public ResponseEntity<Course> addCourse(@RequestBody Course course) {
        return ResponseEntity.ok(courseRepo.save(course));
    }

    // Get all courses
    @GetMapping("/courses")
    public List<Course> getCourses() {
        return courseRepo.findAll();
    }
}