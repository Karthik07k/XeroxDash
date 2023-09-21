package com.college.GetTheCopy.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.college.GetTheCopy.model.Student;
import com.college.GetTheCopy.model.User;
import com.college.GetTheCopy.model.XeroxShop;
import com.college.GetTheCopy.repository.StudentRepository;
@Service
public class StudentService {
	
		@Autowired
		private PasswordEncoder passwordEncoder;

		@Autowired
		private StudentRepository studentRepository;

		private static final Logger logger = LoggerFactory.getLogger(StudentService.class);

		public Student createStudent(Student student) {
			logger.info("Entered Service -> createStudent  Method");
			student.setPassword(passwordEncoder.encode(student.getPassword()));
			logger.info("save Method called");
			logger.info("Exit Service -> createStudent  Method");
			return studentRepository.save(student);
		}
		public Student fetchStudentByStudentID(String studentId) {
			logger.info("Entered Service -> fetchStudentByStudentID Method");
			logger.info("fetchStudentByStudentID Method called");
			logger.info("Exit Service ->  fetchStudentByStudentID Method");
			return studentRepository.findByStudentId(studentId);
		}
		public Long getStudentCount() {
			return studentRepository.count();
		}
		
		public List<Student> fetchStudents() {
			logger.info("Entered Service -> fetchStudents Method");
			logger.info("fetchStudents Method called");
			logger.info("Exit Service ->  fetchStudents Method");
			return studentRepository.findAll();
		}
}
