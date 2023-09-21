package com.college.GetTheCopy.security;

public class TokenProvider {

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

	public TokenProvider(String accessToken, String refreshToken) {
		super();
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
	}

	public TokenProvider() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "TokenProvider [accessToken=" + accessToken + ", refreshToken=" + refreshToken + "]";
	}

}
