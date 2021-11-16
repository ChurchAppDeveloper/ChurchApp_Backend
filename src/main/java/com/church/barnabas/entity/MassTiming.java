package com.church.barnabas.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "mass_timing", catalog = "churchapp")
public class MassTiming implements java.io.Serializable{
	
	private static final long serialVersionUID = 1L;
	private Integer massTimingId;	
	private Date startTime;
	private Date endTime;
	private Date startDate;
	private Date endDate;
	private String scheduleType; 
	private String primaryColour;
	private String secondaryColour;

	
	public MassTiming() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "mass_timing_id", unique = true, nullable = false)
	public Integer getMassTimingId() {
		return massTimingId;
	}
	public void setMassTimingId(Integer massTimingId) {
		this.massTimingId = massTimingId;
	}


	

	@Column(name = "start_time", length = 19)
	@Temporal(TemporalType.TIMESTAMP)
	public Date getStartTime() {
		return startTime;
	}




	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}



	@Column(name = "end_time", length = 19)
	@Temporal(TemporalType.TIMESTAMP)
	public Date getEndTime() {
		return endTime;
	}




	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}



	@Column(name = "schedule_type", length = 60)
	public String getScheduleType() {
		return scheduleType;
	}



	public void setScheduleType(String scheduleType) {
		this.scheduleType = scheduleType;
	}
	@Column(name = "startDate", length = 50)
	@Temporal(TemporalType.TIMESTAMP)
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	@Column(name = "endDate", length = 50)
	@Temporal(TemporalType.TIMESTAMP)
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
    @Column(name="primaryColour")
	public String getPrimaryColour() {
		return primaryColour;
	}

	public void setPrimaryColour(String primaryColour) {
		this.primaryColour = primaryColour;
	}
	@Column(name="secondaryColour")
	public String getSecondaryColour() {
		return secondaryColour;
	}

	public void setSecondaryColour(String secondaryColour) {
		this.secondaryColour = secondaryColour;
	}
	
	
	
	
	
	
	


	

	
}
