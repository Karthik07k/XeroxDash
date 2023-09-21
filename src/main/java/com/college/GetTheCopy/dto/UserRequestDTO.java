package com.college.GetTheCopy.dto;

import java.util.List;

import com.college.GetTheCopy.dto.Student.UserStudentRequestDTO;
import com.college.GetTheCopy.dto.XeroxShop.UserShopRequestDTO;
import com.college.GetTheCopy.model.DegreeEnum;
import com.college.GetTheCopy.model.Role;

public class UserRequestDTO {
	private String emailId;

	private String name;

	private String mobileNumber;

	private String password;

	private String address;

	private List<Role> roles;

	private UserShopRequestDTO shop;

	private UserStudentRequestDTO student;

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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public UserShopRequestDTO getShop() {
		return shop;
	}

	public void setShop(UserShopRequestDTO shop) {
		this.shop = shop;
	}

	public UserStudentRequestDTO getStudent() {
		return student;
	}

	public void setStudent(UserStudentRequestDTO student) {
		this.student = student;
	}

	@Override
	public String toString() {
		return "UserRequestDTO [emailId=" + emailId + ", name=" + name + ", mobileNumber=" + mobileNumber
				+ ", password=" + password + ", address=" + address + ", roles=" + roles + ", shop=" + shop
				+ ", student=" + student + "]";
	}

	

}
