package com.college.GetTheCopy.dto.xeroxpreferences;

import com.college.GetTheCopy.model.PaperSideEnum;
import com.college.GetTheCopy.model.PaperSizeEnum;

public class XeroxPreferenceRequestDTO {

	private String id;

	private String fileDetailID;

	private PaperSizeEnum size;

	private PaperSideEnum side;

	private Boolean isColor;

	private Integer copy;

	private String shopId;

	private String userId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFileDetailID() {
		return fileDetailID;
	}

	public void setFileDetailID(String fileDetailID) {
		this.fileDetailID = fileDetailID;
	}

	public PaperSizeEnum getSize() {
		return size;
	}

	public void setSize(PaperSizeEnum size) {
		this.size = size;
	}

	public PaperSideEnum getSide() {
		return side;
	}

	public void setSide(PaperSideEnum side) {
		this.side = side;
	}

	public Boolean getIsColor() {
		return isColor;
	}

	public void setIsColor(Boolean isColor) {
		this.isColor = isColor;
	}

	public Integer getCopy() {
		return copy;
	}

	public void setCopy(Integer copy) {
		this.copy = copy;
	}

	public String getShopId() {
		return shopId;
	}

	public void setShopId(String shopId) {
		this.shopId = shopId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public XeroxPreferenceRequestDTO(String id, String fileDetailID, PaperSizeEnum size, PaperSideEnum side,
			Boolean isColor, Integer copy, String shopId, String userId) {
		super();
		this.id = id;
		this.fileDetailID = fileDetailID;
		this.size = size;
		this.side = side;
		this.isColor = isColor;
		this.copy = copy;
		this.shopId = shopId;
		this.userId = userId;
	}

	public XeroxPreferenceRequestDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "XeroxPreferenceRequestDTO [id=" + id + ", fileDetailID=" + fileDetailID + ", size=" + size + ", side="
				+ side + ", isColor=" + isColor + ", copy=" + copy + ", shopId=" + shopId
				+ ", userId=" + userId + "]";
	}

}
