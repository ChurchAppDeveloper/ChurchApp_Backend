package com.church.barnabas.dto;



import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
//Causes Lombok to generate the toString() method.
@ToString
//Causes Lombok to generate a constructor with 1 parameter for each field in your class.
@AllArgsConstructor
@NoArgsConstructor
public class MassTimingDto {
	
		@NonNull
	    @JsonProperty("id")
		private Integer id;
	
		
		@JsonProperty("startDate")
		private String startDate;
		
		@JsonProperty("endDate")
		private String endDate;
		
	    @JsonProperty("start_time")
		private String startTime;  
		
		
	    @JsonProperty("end_time")
		private String endTime;
	    
	    @JsonProperty("primaryColour")
	    private String primaryColour;
	    
	    @JsonProperty("secondaryColour")
	    private String secondaryColour;
	    
	   
	    
	  
	    @JsonProperty("schedule_type")
	    private String scheduleType;


}
