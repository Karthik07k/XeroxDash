package com.college.GetTheCopy.common.dto;

public class ResetPasswordDTO {

	private String password;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public ResetPasswordDTO(String password) {
		super();
		this.password = password;
	}

	public ResetPasswordDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "ResetPasswordDTO [password=" + password + "]";
	}

}
