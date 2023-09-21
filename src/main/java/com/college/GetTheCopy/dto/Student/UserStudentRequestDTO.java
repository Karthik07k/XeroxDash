package com.college.GetTheCopy.dto.Student;

import com.college.GetTheCopy.model.DegreeEnum;

public class UserStudentRequestDTO {

	private DegreeEnum degree;

	private Integer sem;

	private String college;

	@Override
	public String toString() {
		return "UserStudentRequestDTO [degree=" + degree + ", sem=" + sem + ", college=" + college + "]";
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

	public UserStudentRequestDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UserStudentRequestDTO(DegreeEnum degree, Integer sem, String college) {
		super();
		this.degree = degree;
		this.sem = sem;
		this.college = college;
	}
	

}
