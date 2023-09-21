package com.college.GetTheCopy.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.college.GetTheCopy.model.Student;
import com.college.GetTheCopy.model.XeroxShop;

public interface StudentRepository extends CrudRepository<Student, String>{
	boolean existsBystudentId(String studentId);

	Student findByStudentId(String studentId);
	List<Student> findAll();
	

}
