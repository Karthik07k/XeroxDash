package com.college.GetTheCopy.common.dto;

public class OauthTokenRequest {

	private String email;
	private String password;
	private String grant_type;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getGrant_type() {
		return grant_type;
	}

	public void setGrant_type(String grant_type) {
		this.grant_type = grant_type;
	}

	public OauthTokenRequest(String email, String password, String grant_type) {
		super();
		this.email = email;
		this.password = password;
		this.grant_type = grant_type;
	}

	public OauthTokenRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "OauthTokenRequest [email=" + email + ", password=" + password + ", grant_type=" + grant_type + "]";
	}

}
