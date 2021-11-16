package com.church.barnabas.dto;

import java.sql.Time;
import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ConfessionDto {
	
	private Integer confessionId;
	@Temporal(TemporalType.TIMESTAMP)
	private Date confessionDate;
	private Time confessionStartTime;
	private Time confessionEndTime;
	private Integer confessionDuration;

	

}
