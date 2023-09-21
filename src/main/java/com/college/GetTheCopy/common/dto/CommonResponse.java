package com.college.GetTheCopy.common.dto;

public class CommonResponse {

	private Boolean isSuccessful;

	private String responseText;

	public Boolean getIsSuccessful() {
		return isSuccessful;
	}

	public void setIsSuccessful(Boolean isSuccessful) {
		this.isSuccessful = isSuccessful;
	}

	public String getResponseText() {
		return responseText;
	}

	public void setResponseText(String responseText) {
		this.responseText = responseText;
	}

	@Override
	public String toString() {
		return "CommonResponse [isSuccessful=" + isSuccessful + ", responseText=" + responseText + "]";
	}

	public CommonResponse(Boolean isSuccessful, String responseText) {
		super();
		this.isSuccessful = isSuccessful;
		this.responseText = responseText;
	}

	public CommonResponse() {
		super();
	}

}
