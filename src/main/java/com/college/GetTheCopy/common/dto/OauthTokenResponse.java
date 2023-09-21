package com.college.GetTheCopy.common.dto;

public class OauthTokenResponse {

	private String accessToken;

	private String refreshToken;

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	@Override
	public String toString() {
		return "OauthTokenResponse [accessToken=" + accessToken + ", refreshToken=" + refreshToken + "]";
	}

	public OauthTokenResponse(String accessToken, String refreshToken) {
		super();
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
	}

	public OauthTokenResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

}
