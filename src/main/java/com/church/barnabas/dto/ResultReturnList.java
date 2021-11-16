package com.church.barnabas.dto;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

//Lombok annotations
//Causes Lombok to generate getter() methods.
@Getter
//Causes Lombok to generate the toString() method.
@ToString
//Causes Lombok to generate a constructor with 1 parameter for each field in your class.
@AllArgsConstructor
public class ResultReturnList {
	@NonNull
    @JsonProperty("success")
	private Boolean success;
	@NonNull
    @JsonProperty("message")
	private String message;
	@NonNull
    @JsonProperty("content")
	private List<?> list;
    
	
	
}
