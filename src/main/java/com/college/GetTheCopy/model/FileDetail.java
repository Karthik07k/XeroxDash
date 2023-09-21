package com.college.GetTheCopy.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class FileDetail {

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String id;
	private String name;
	private long size;
	private String url;
	private FileFormatEnum format;
	private Integer pages;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public FileFormatEnum getFormat() {
		return format;
	}

	public void setFormat(FileFormatEnum format) {
		this.format = format;
	}

	public Integer getPages() {
		return pages;
	}

	public void setPages(Integer pages) {
		this.pages = pages;
	}

	public FileDetail(String name, long size, String url, FileFormatEnum format, Integer pages) {
		super();
		this.name = name;
		this.size = size;
		this.url = url;
		this.format = format;
		this.pages = pages;
	}

	@Override
	public String toString() {
		return "FileDetail [id=" + id + ", name=" + name + ", size=" + size + ", url=" + url + ", format=" + format
				+ ", pages=" + pages + "]";
	}

	public FileDetail() {
		super();
		// TODO Auto-generated constructor stub
	}

}
