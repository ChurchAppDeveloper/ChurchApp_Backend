package com.church.barnabas.dto;



import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserListDto {
	private Integer id;
   
	private String name;
   
	private String email;
  
	private String contactNo;
 
	private String address;
	
	
	private String password;
	
	private Boolean activeStatus;
}
