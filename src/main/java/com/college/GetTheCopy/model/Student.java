package com.college.GetTheCopy.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;

@Entity
public class Student extends User{

	@Column(unique = true)
	private String studentId;
	
	private DegreeEnum degree;
	
	private Integer sem;
	
	private String college;

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public DegreeEnum getDegree() {
		return degree;
	}

	public void setDegree(DegreeEnum degree) {
		this.degree = degree;
	}

	public Integer getSem() {
		return sem;
	}

	public void setSem(Integer sem) {
		this.sem = sem;
	}

	public String getCollege() {
		return college;
	}

	public void setCollege(String college) {
		this.college = college;
	}

	@Override
	public String toString() {
		return "Student [studentId=" + studentId + ", degree=" + degree + ", sem=" + sem + ", college=" + college + "]";
	}

	public Student(String id, String emailId, String name, String mobileNumber, String password, String address,
			List<Role> roles, String studentId, DegreeEnum degree, Integer sem, String college) {
		super(id, emailId, name, mobileNumber, password, address, roles);
		this.studentId = studentId;
		this.degree = degree;
		this.sem = sem;
		this.college = college;
	}

	public Student() {
		// TODO Auto-generated constructor stub
	}
	
}
