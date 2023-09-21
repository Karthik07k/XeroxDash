package com.college.GetTheCopy.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

@Entity
@DynamicUpdate
public class XeroxPreference {

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String id;

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH,
			CascadeType.DETACH }, optional = true)
	@JoinColumn(name = "fileDetailId")
	private FileDetail fileDetail;

	private PaperSizeEnum size;

	private Integer pageCount;

	private PaperSideEnum side;

	private Boolean isColor;

	private Integer copy;

	private Double price;

	private Boolean isPaid;

	@OneToOne(cascade = { CascadeType.MERGE })
	@JoinColumn(name = "shopId")
	private XeroxShop xeroxShop;

	@OneToOne(cascade = { CascadeType.MERGE })
	@JoinColumn(name = "id")
	private User user;

	private TransactionStatusEnum status;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public FileDetail getFileDetail() {
		return fileDetail;
	}

	public void setFileDetail(FileDetail fileDetail) {
		this.fileDetail = fileDetail;
	}

	public PaperSizeEnum getSize() {
		return size;
	}

	public void setSize(PaperSizeEnum size) {
		this.size = size;
	}

	public Integer getPageCount() {
		return pageCount;
	}

	public void setPageCount(Integer pageCount) {
		this.pageCount = pageCount;
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

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Boolean getIsPaid() {
		return isPaid;
	}

	public void setIsPaid(Boolean isPaid) {
		this.isPaid = isPaid;
	}

	public XeroxShop getXeroxShop() {
		return xeroxShop;
	}

	public void setXeroxShop(XeroxShop xeroxShop) {
		this.xeroxShop = xeroxShop;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public TransactionStatusEnum getStatus() {
		return status;
	}

	public void setStatus(TransactionStatusEnum status) {
		this.status = status;
	}

	public XeroxPreference(String id, FileDetail fileDetail, PaperSizeEnum size, Integer pageCount, PaperSideEnum side,
			Boolean isColor, Integer copy, Double price, Boolean isPaid, XeroxShop xeroxShop, User user,
			TransactionStatusEnum status) {
		super();
		this.id = id;
		this.fileDetail = fileDetail;
		this.size = size;
		this.pageCount = pageCount;
		this.side = side;
		this.isColor = isColor;
		this.copy = copy;
		this.price = price;
		this.isPaid = isPaid;
		this.xeroxShop = xeroxShop;
		this.user = user;
		this.status = status;
	}

	public XeroxPreference() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "XeroxPreference [id=" + id + ", fileDetail=" + fileDetail + ", size=" + size + ", pageCount="
				+ pageCount + ", side=" + side + ", isColor=" + isColor + ", copy=" + copy + ", price=" + price
				+ ", isPaid=" + isPaid + ", xeroxShop=" + xeroxShop + ", user=" + user + ", status=" + status + "]";
	}

}
