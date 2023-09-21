package com.college.GetTheCopy.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import lombok.Getter;
import lombok.Setter;



@Entity
@Table(name = "note")
@DynamicUpdate
@Getter
@Setter
public class Note {
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String id;

	@OneToOne(cascade = { CascadeType.MERGE })
	@JoinColumn(name = "fileDetailId")
	private FileDetail fileDetail;
	
	
	@OneToOne(cascade = { CascadeType.MERGE })
	@JoinColumn(name = "studentId")
	private Student student; 
	
	
	private DegreeEnum degree;
	
	private Integer sem;
	
	private String subject;
	
	private Integer rating;
	
	private GeneralStatusEnum status;
	
	

}

