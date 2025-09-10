package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Application;
import com.example.demo.model.Course;
import com.example.demo.model.Student;
import com.example.demo.repository.ApplicationRepository;
import com.example.demo.repository.CourseRepository;
import com.example.demo.repository.StudentRepository;

@Service
public class StudentService {
	
	@Autowired
    private StudentRepository studentRepo;
    @Autowired
    private ApplicationRepository appRepo;
    @Autowired
    private CourseRepository courseRepo;
    @Autowired
	private StudentRepository studentRepository;

	public List<Student> getAllStudents() { 
		
		return studentRepo.findAll(); 
		
	}

	public Student registerStudent(Student s) { 
		
		return studentRepo.save(s); 
		
	}

	public List<Application> applyForCourse(Long studentId, Long courseId) {
	    // logic to create application
		Student student = studentRepo.findById(studentId).orElseThrow();
	    Course course = courseRepo.findById(courseId).orElseThrow();

	    Application app = new Application();
	    app.setStudent(student);
	    app.setCourse(course);
	    app.setStatus("Pending");

	    appRepo.save(app);
	    return List.of(app);

	}

	public List<Application> getPendingApplications() {
	    return appRepo.findByStatus("Pending");
	}

	public Student saveStudent(Student student) {
	    return studentRepository.save(student);
	}
	
	public Student getStudentById(Long id) {
	    return studentRepository.findById(id).orElse(null);
	}

}


