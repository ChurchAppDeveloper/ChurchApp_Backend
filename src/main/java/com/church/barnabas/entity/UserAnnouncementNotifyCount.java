package com.church.barnabas.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "user_announcement_notification_count")
public class UserAnnouncementNotifyCount implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4918730034269767164L;
	private Integer id;
	private String deviceId;
	private UserAnnouncement userAnnouncement;
	private boolean status;

	@Column(name = "status")
	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "announcement_id", referencedColumnName = "id")
	public UserAnnouncement getUserAnnouncement() {
		return userAnnouncement;
	}

	public void setUserAnnouncement(UserAnnouncement userAnnouncement) {
		this.userAnnouncement = userAnnouncement;
	}

	@Column(name = "device_id", length = 100)
	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

}
