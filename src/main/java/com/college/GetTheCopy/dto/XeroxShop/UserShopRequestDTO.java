package com.college.GetTheCopy.dto.XeroxShop;

public class UserShopRequestDTO {

	private String shopName;

	private String shopAddress;

	private Float startFrom;

	private Float endAt;

	private Float costPerPage;

	private Boolean isShopOpen;

	private Integer metersAway;

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

	public UserShopRequestDTO(String shopName, String shopAddress, Float startFrom, Float endAt, Float costPerPage,
			Boolean isShopOpen, Integer metersAway) {
		super();
		this.shopName = shopName;
		this.shopAddress = shopAddress;
		this.startFrom = startFrom;
		this.endAt = endAt;
		this.costPerPage = costPerPage;
		this.isShopOpen = isShopOpen;
		this.metersAway = metersAway;
	}

	public UserShopRequestDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "UserShopRequestDTO [shopName=" + shopName + ", shopAddress=" + shopAddress + ", startFrom=" + startFrom
				+ ", endAt=" + endAt + ", costPerPage=" + costPerPage + ", isShopOpen=" + isShopOpen + ", metersAway="
				+ metersAway + "]";
	}

}
