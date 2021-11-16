package com.church.barnabas.dto;



import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ClassifierDto {
	private Integer id;
	
	private Integer businessTypeId;
	
	private String businessTypeName;
	private String businessName;
	private String phoneNumber;
	private String imagename;
}
