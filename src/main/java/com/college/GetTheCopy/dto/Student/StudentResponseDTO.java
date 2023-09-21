package com.college.GetTheCopy.dto.Student;

import com.college.GetTheCopy.model.DegreeEnum;

public class StudentResponseDTO {
	
	private String id;
	
	private String emailId;

	private String name;

	private String mobileNumber;

	private String address;
    
	private String studentId;
	
	private DegreeEnum degree;
	
	private Integer sem;
	
	private String college;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

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

	public StudentResponseDTO(String id, String emailId, String name, String mobileNumber, String address,
			String studentId, DegreeEnum degree, Integer sem, String college) {
		super();
		this.id = id;
		this.emailId = emailId;
		this.name = name;
		this.mobileNumber = mobileNumber;
		this.address = address;
		this.studentId = studentId;
		this.degree = degree;
		this.sem = sem;
		this.college = college;
	}

	@Override
	public String toString() {
		return "StudentResponseDTO [id=" + id + ", emailId=" + emailId + ", name=" + name + ", mobileNumber="
				+ mobileNumber + ", address=" + address + ", studentId=" + studentId + ", degree=" + degree + ", sem="
				+ sem + ", college=" + college + "]";
	}

	public StudentResponseDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	
}
