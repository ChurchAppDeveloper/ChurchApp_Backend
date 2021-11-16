package com.church.barnabas.dto;

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
public class ResultReturnObject {
	@NonNull
    @JsonProperty("success")
	private Boolean success;
	@NonNull
    @JsonProperty("message")
	private String message;
	@NonNull
    @JsonProperty("content")
	private Object content;

}
