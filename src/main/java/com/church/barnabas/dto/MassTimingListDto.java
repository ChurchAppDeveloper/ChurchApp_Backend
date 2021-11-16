package com.church.barnabas.dto;

import java.util.ArrayList;
import java.util.List;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@Getter
@Setter
//Causes Lombok to generate the toString() method.
@ToString
//Causes Lombok to generate a constructor with 1 parameter for each field in your class.
@AllArgsConstructor
@NoArgsConstructor
public class MassTimingListDto {
//	@NonNull
//    @JsonProperty("id")
//	private Integer id;
	
	
	private List<MassTimingDto> monday=new ArrayList<>();
	
	private List<MassTimingDto> tuesday=new ArrayList<>();

	private List<MassTimingDto> wednesday=new ArrayList<>();

	private List<MassTimingDto> thursday=new ArrayList<>();

	private List<MassTimingDto> friday=new ArrayList<>();

	private List<MassTimingDto> saturday=new ArrayList<>();

	private List<MassTimingDto> sunday=new ArrayList<>();

	
}
