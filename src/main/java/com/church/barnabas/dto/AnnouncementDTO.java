package com.church.barnabas.dto;

import java.util.Set;



import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor

public class AnnouncementDTO {

	private Integer id;
	private String title;
	private String description;
	private Set<String> urls;
	private boolean status;
	

	
}
