package com.example.demo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Application;
import com.example.demo.model.Course;
import com.example.demo.model.Student;
import com.example.demo.repository.ApplicationRepository;

@Service
public class AdminService {
	
	@Autowired
    private ApplicationRepository appRepo;

	public void evaluateApplications() {
	    List<Application> apps = appRepo.findAll();
	    for (Application app : apps) {
	        if (app.getStudent().getMarks() >= app.getCourse().getCutoffMarks()) {
	            app.setStatus("Approved");
	        } else {
	            app.setStatus("Rejected");
	        }
	        appRepo.save(app);
	    }
	}
	
	public List<Application> getPendingApplications() {
        return appRepo.findAll().stream()
                      .filter(app -> "Pending".equalsIgnoreCase(app.getStatus()))
                      .collect(Collectors.toList());
    }
	
	public void validateApplication(Application app) {
	    Student student = app.getStudent();
	    Course appliedCourse = app.getCourse();

	    if (student != null && student.getCourse() != null && appliedCourse != null) {
	        if (!student.getCourse().getId().equals(appliedCourse.getId())) {
	            throw new IllegalArgumentException("Student is already registered for a different course.");
	        }
	    }
	}

}
