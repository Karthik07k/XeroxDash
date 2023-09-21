package com.college.GetTheCopy.model;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
	ROLE_ADMIN,ROLE_CUSTOMER,ROLE_SHOP,ROLE_STUDENT;

  public String getAuthority() {
    return name();
  }

}