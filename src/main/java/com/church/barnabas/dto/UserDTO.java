package com.church.barnabas.dto;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;
import lombok.Setter;
//Lombok annotations
//Causes Lombok to generate getter() methods.
@Getter
@Setter
//Causes Lombok to generate the toString() method.
@ToString
//Causes Lombok to generate a constructor with 1 parameter for each field in your class.
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    @NonNull
    @JsonProperty("id")
	private Integer id;
    @NonNull
    @JsonProperty("name")
	private String name;
    @NonNull
    @JsonProperty("email")
	private String email;
    @NonNull
    @JsonProperty("contact_no")
	private String contactNo;
    @NonNull
    @JsonProperty("otp")
	private String otp;
    @NonNull
    @JsonProperty("address")
	private String address;
    @NonNull
    @JsonProperty("otp_expiry")
	private Timestamp otpExpiry;
	
}
