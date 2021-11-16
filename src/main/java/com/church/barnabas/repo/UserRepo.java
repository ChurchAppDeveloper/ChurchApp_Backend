package com.church.barnabas.repo;

import java.sql.Timestamp;

import org.springframework.data.jpa.repository.JpaRepository;

import com.church.barnabas.entity.UserTable;
import org.springframework.data.jpa.repository.Query;

public interface UserRepo extends JpaRepository<UserTable, Integer> {
	
	UserTable findByContactNo(String contactno);
	
//	UserTable findByContactNoAndActiveStatus(String contactno);

	
	@Query(value="select i from UserTable i where i.contactNo=?1 and i.otpExpiry between ?2 and ?3")
	UserTable findByContactNoAndOtpExpiryBetween2minutes(String contactno,Timestamp startdate,Timestamp enddate);

	
	@Query(value="select i from UserTable i where i.contactNo=?1 and i.otp=?2 and i.activeStatus=?3")
	UserTable findByContactNoAndOtpAndActiveStatus(String contactno,String password,Boolean activeStatus);
	
}
