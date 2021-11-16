package com.church.barnabas.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "confession", catalog = "churchapp")
public class Confession implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "confession_id", unique = true, nullable = false)
	private Integer confessionId;
	
	@Column(name = "confession_date", length = 25)
	@Temporal(TemporalType.DATE)
	private Date confessionDate;
	
	@Column(name = "confession_start_time", length = 25)
	private Time confessionStartTime;
	
	@Column(name = "confession_end_time", length = 25)
	private Time confessionEndTime;
	
	@Column(name = "confession_duration", length = 25)
	private Integer confessionDuration;

	
	@Column(name = "created_by", length = 10)
	private Integer createdBy;
	
	@Column(name = "modified_by", length = 10)
	private Integer modifiedBy;
	
	@Column(name = "created_time", length = 25)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdTime;
	
	@Column(name = "modified_time", length = 25)
	@Temporal(TemporalType.TIMESTAMP)
	private Date modifiedTime;

	public Confession() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	







	public Confession(Integer confessionId, Date confessionDate, Time confessionStartTime, Time confessionEndTime,
			Integer confessionDuration, Integer createdBy, Integer modifiedBy, Date createdTime, Date modifiedTime) {
		super();
		this.confessionId = confessionId;
		this.confessionDate = confessionDate;
		this.confessionStartTime = confessionStartTime;
		this.confessionEndTime = confessionEndTime;
		this.confessionDuration = confessionDuration;
		this.createdBy = createdBy;
		this.modifiedBy = modifiedBy;
		this.createdTime = createdTime;
		this.modifiedTime = modifiedTime;
	}










	public Integer getConfessionId() {
		return confessionId;
	}

	public void setConfessionId(Integer confessionId) {
		this.confessionId = confessionId;
	}

	public Date getConfessionDate() {
		return confessionDate;
	}

	public void setConfessionDate(Date confessionDate) {
		this.confessionDate = confessionDate;
	}

	public Time getConfessionStartTime() {
		return confessionStartTime;
	}

	public void setConfessionStartTime(Time confessionStartTime) {
		this.confessionStartTime = confessionStartTime;
	}

	public Time getConfessionEndTime() {
		return confessionEndTime;
	}

	public void setConfessionEndTime(Time confessionEndTime) {
		this.confessionEndTime = confessionEndTime;
	}

	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	public Integer getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(Integer modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public Date getModifiedTime() {
		return modifiedTime;
	}

	public void setModifiedTime(Date modifiedTime) {
		this.modifiedTime = modifiedTime;
	}










	public Integer getConfessionDuration() {
		return confessionDuration;
	}










	public void setConfessionDuration(Integer confessionDuration) {
		this.confessionDuration = confessionDuration;
	}




	
	
}
