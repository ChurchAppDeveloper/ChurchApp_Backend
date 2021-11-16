package com.church.barnabas.dto;

import lombok.Data;

import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserAnnouncementReadDTO {
	private Integer announcementId;
	private String deviceId;
	private boolean status;
	
}
