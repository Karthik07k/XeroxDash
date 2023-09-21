package com.college.GetTheCopy.utility;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FromPath {
	@Value("${default.from.email}")
	private String path;
	
	@Value("${default.bcc.email}")
	private String bccPath;

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getBccPath() {
		return bccPath;
	}

	public void setBccPath(String bccPath) {
		this.bccPath = bccPath;
	}


}