package com.college.GetTheCopy.dto.XeroxShop;

import com.college.GetTheCopy.model.GeneralStatusEnum;

public class XeroxShopResponseDTO {

	private String id;

	private String emailId;

	private String name;

	private String mobileNumber;

	private String address;

	private String shopId;

	private String shopName;

	private String shopAddress;

	private Float startFrom;

	private Float endAt;

	private Float costPerPage;

	private Boolean isShopOpen;

	private Integer metersAway;

	private GeneralStatusEnum status;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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

	public XeroxShopResponseDTO(String id, String emailId, String name, String mobileNumber, String address,
			String shopId, String shopName, String shopAddress, Float startFrom, Float endAt, Float costPerPage,
			Boolean isShopOpen, Integer metersAway, GeneralStatusEnum status) {
		super();
		this.id = id;
		this.emailId = emailId;
		this.name = name;
		this.mobileNumber = mobileNumber;
		this.address = address;
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

	@Override
	public String toString() {
		return "XeroxShopResponseDTO [id=" + id + ", emailId=" + emailId + ", name=" + name + ", mobileNumber="
				+ mobileNumber + ", address=" + address + ", shopId=" + shopId + ", shopName=" + shopName
				+ ", shopAddress=" + shopAddress + ", startFrom=" + startFrom + ", endAt=" + endAt + ", costPerPage="
				+ costPerPage + ", isShopOpen=" + isShopOpen + ", metersAway=" + metersAway + ", status=" + status
				+ "]";
	}

	public XeroxShopResponseDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

}
