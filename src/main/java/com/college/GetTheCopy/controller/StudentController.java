package com.college.GetTheCopy.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.college.GetTheCopy.dto.Student.StudentResponseDTO;
import com.college.GetTheCopy.model.Student;
import com.college.GetTheCopy.service.StudentService;

@RestController
@RequestMapping("api/v1")
public class StudentController {

	private static final Logger logger = LoggerFactory.getLogger(XeroxShopController.class);

	@Autowired
	StudentService studentService;

	@GetMapping("/students/{studentId}")
	public ResponseEntity<?> fetchStudentByStudentID(@PathVariable String studentId) throws IOException {
		logger.info("Entered CommonController-> fetchStudentByStudentID  Method");

		try {
			logger.info("fetchStudentByStudentID Method called");
			Student student = studentService.fetchStudentByStudentID(studentId);

			StudentResponseDTO studentResponseDTO = getStudentResponseDTO(student);
			logger.info("Exit CommonController-> fetchStudentByStudentID  Method");

			return new ResponseEntity<StudentResponseDTO>(studentResponseDTO, HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Exception Occured: " + e);
			logger.info("Exit CommonController-> fetchStudentByStudentID  Method");
			throw e;
		}

	}

//	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping(value = "/students")
	public ResponseEntity<?> getStudents() throws IOException {
		logger.info("Entered CommonController-> fetchStudents  Method");

		try {
			List<Student> studentList = new ArrayList<Student>();

			logger.info("fetchStudents Method called");
			List<Student> students = studentService.fetchStudents();

			List<StudentResponseDTO> studentResponseDTOs = getStudentResponseDTOs(students);

			logger.info("Exit CommonController-> fetchStudents  Method");

			return new ResponseEntity<List<StudentResponseDTO>>(studentResponseDTOs, HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Exception Occured: " + e);
			logger.info("Exit CommonController-> fetchStudents  Method");
			throw e;
		}

	}

	public List<StudentResponseDTO> getStudentResponseDTOs(List<Student> students) {

		List<StudentResponseDTO> studentResponseDTOs = new ArrayList<StudentResponseDTO>();

		students.forEach(student -> studentResponseDTOs.add(getStudentResponseDTO(student)));

		return studentResponseDTOs;
	}

	public StudentResponseDTO getStudentResponseDTO(Student student) {

		StudentResponseDTO studentResponseDTO = new StudentResponseDTO();

		studentResponseDTO.setName(student.getName());
		studentResponseDTO.setEmailId(student.getUserName());
		studentResponseDTO.setId(student.getId());
		studentResponseDTO.setAddress(student.getAddress());
		studentResponseDTO.setMobileNumber(student.getMobileNumber());
		studentResponseDTO.setDegree(student.getDegree());
		studentResponseDTO.setSem(student.getSem());
		studentResponseDTO.setCollege(student.getCollege());
		studentResponseDTO.setStudentId(student.getStudentId());

		return studentResponseDTO;
	}

}
