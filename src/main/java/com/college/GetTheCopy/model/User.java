package com.college.GetTheCopy.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

/**
 * @author karthik kumar
 *
 */
@Entity
@Table(name = "user")
@DynamicUpdate

@Inheritance(strategy = InheritanceType.JOINED)
public class User extends AuditModel {

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String id;

	@Column(unique = true)
	private String userName;

	private String name;

	@Column(unique = true)
	private String mobileNumber;

	private String password;

	private String address;

	@ElementCollection(fetch = FetchType.EAGER)
	private List<Role> roles;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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

	public User(String id, String userName, String name, String mobileNumber, String password, String address,
			List<Role> roles) {
		super();
		this.id = id;
		this.userName = userName;
		this.name = name;
		this.mobileNumber = mobileNumber;
		this.password = password;
		this.address = address;
		this.roles = roles;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", userName=" + userName + ", name=" + name + ", mobileNumber=" + mobileNumber
				+ ", password=" + password + ", address=" + address + ", roles=" + roles + "]";
	}

	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

}
