package com.app.starcom.repository;

import jakarta.persistence.EntityManager;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.app.starcom.model.Student;


@Repository
@Transactional("transactionManager")
public class StudentRepository {
	
	@Autowired
	private EntityManager entityManager;
	
	public void save(Student student) {
		entityManager.persist(student);
	}
	
	public List<Student> getAllStudents() {
		return null;
	}
	
	public void update(Integer studentId, Student updateData) {
		
	}
	
	public void delete(Integer studentId) {
		
	}
	
	public Student getStudentById(Integer studentId) {
		return null;
	}
}
