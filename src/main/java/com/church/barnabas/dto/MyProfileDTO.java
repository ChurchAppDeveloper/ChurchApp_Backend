package com.church.barnabas.dto;


import com.church.barnabas.entity.ChurchProfile;
import com.fasterxml.jackson.annotation.JsonProperty;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;
import lombok.Setter;

@Getter
@Setter
//Causes Lombok to generate the toString() method.
@ToString
//Causes Lombok to generate a constructor with 1 parameter for each field in your class.
@AllArgsConstructor
@NoArgsConstructor
public class MyProfileDTO {

    @JsonProperty("contact_no")
	private String contactNo;  

    @JsonProperty("role_name")
	private String roleName;

    private ChurchProfile churchProfile;
    
    
}
