package com.church.barnabas.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.sql.Time;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "schedule_confession", catalog = "churchapp")
public class ScheduleConfession implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "schedule_confession_id", unique = true, nullable = false)
	private Integer scheduleConfessionId;
	
	@Column(name = "start_time", length = 25)
	private Time startTime;
	
	@Column(name = "end_time", length = 25)
	private Time endTime;
	
	@Column(name = "schedule_duration", length = 25)
	private Integer scheduleDuration;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnore
	@JoinColumn(name = "contributor_id", nullable = false)
	private UserTable contributorId;

	public ScheduleConfession() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ScheduleConfession(Integer scheduleConfessionId, Time startTime, Time endTime, Integer scheduleDuration,
			UserTable contributorId) {
		super();
		this.scheduleConfessionId = scheduleConfessionId;
		this.startTime = startTime;
		this.endTime = endTime;
		this.scheduleDuration = scheduleDuration;
		this.contributorId = contributorId;
	}

	public Integer getScheduleConfessionId() {
		return scheduleConfessionId;
	}

	public void setScheduleConfessionId(Integer scheduleConfessionId) {
		this.scheduleConfessionId = scheduleConfessionId;
	}

	public Time getStartTime() {
		return startTime;
	}

	public void setStartTime(Time startTime) {
		this.startTime = startTime;
	}

	public Time getEndTime() {
		return endTime;
	}

	public void setEndTime(Time endTime) {
		this.endTime = endTime;
	}

	public Integer getScheduleDuration() {
		return scheduleDuration;
	}

	public void setScheduleDuration(Integer scheduleDuration) {
		this.scheduleDuration = scheduleDuration;
	}

	public UserTable getContributorId() {
		return contributorId;
	}

	public void setContributorId(UserTable contributorId) {
		this.contributorId = contributorId;
	}

}
