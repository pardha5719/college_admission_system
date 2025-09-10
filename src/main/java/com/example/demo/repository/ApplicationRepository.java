package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.model.Application;

public interface ApplicationRepository extends JpaRepository<Application, Long> {

	List<Application> findByStatus(String string);
	
	@Query("SELECT COUNT(a) FROM Application a WHERE a.status = 'PENDING'")
	long countPending();
	
	boolean existsByStudentIdAndCourseId(Long studentId, Long courseId);
}

