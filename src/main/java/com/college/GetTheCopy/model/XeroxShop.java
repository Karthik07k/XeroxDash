package com.college.GetTheCopy.model;

import java.util.List;

import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class XeroxShop extends User {

	private String shopId;

	private String shopName;

	private String shopAddress;

	private Float startFrom;

	private Float endAt;

	private Float costPerPage;

	private Boolean isShopOpen;

	private Integer metersAway;

	private GeneralStatusEnum status;

	public XeroxShop(String id, String emailId, String name, String mobileNumber, String password, String address,
			List<Role> roles, String shopId, String shopName, String shopAddress, Float startFrom, Float endAt,
			Float costPerPage, Boolean isShopOpen, Integer metersAway, GeneralStatusEnum status) {
		super(id, emailId, name, mobileNumber, password, address, roles);
		this.shopId = shopId;
		this.shopName = shopName;
		this.shopAddress = shopAddress;
		this.startFrom = startFrom;
		this.endAt = endAt;
		this.costPerPage = costPerPage;
		this.isShopOpen = isShopOpen;
		this.metersAway = metersAway;
		this.status = status;
	}

	public XeroxShop() {
		// TODO Auto-generated constructor stub
	}

	public String getShopId() {
		return shopId;
	}

	public void setShopId(String shopId) {
		this.shopId = shopId;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getShopAddress() {
		return shopAddress;
	}

	public void setShopAddress(String shopAddress) {
		this.shopAddress = shopAddress;
	}

	public Float getStartFrom() {
		return startFrom;
	}

	public void setStartFrom(Float startFrom) {
		this.startFrom = startFrom;
	}

	public Float getEndAt() {
		return endAt;
	}

	public void setEndAt(Float endAt) {
		this.endAt = endAt;
	}

	public Float getCostPerPage() {
		return costPerPage;
	}

	public void setCostPerPage(Float costPerPage) {
		this.costPerPage = costPerPage;
	}

	public Boolean getIsShopOpen() {
		return isShopOpen;
	}

	public void setIsShopOpen(Boolean isShopOpen) {
		this.isShopOpen = isShopOpen;
	}

	public Integer getMetersAway() {
		return metersAway;
	}

	public void setMetersAway(Integer metersAway) {
		this.metersAway = metersAway;
	}

	public GeneralStatusEnum getStatus() {
		return status;
	}

	public void setStatus(GeneralStatusEnum status) {
		this.status = status;
	}

}
