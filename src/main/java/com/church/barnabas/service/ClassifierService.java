package com.church.barnabas.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.church.barnabas.dto.BusinessTypeDto;
import com.church.barnabas.dto.ResultReturnList;
import com.church.barnabas.dto.ResultReturnObject;

public interface ClassifierService {
	
	ResultReturnObject createBusinessType(BusinessTypeDto businessTypeDto,String contactNo) ;
	
	ResultReturnList businessTypeList(String contactNo,String search) ;
	
	ResultReturnObject deleteBusinessType(Integer businessTypeId,String contactNo) ;
	
	
	ResultReturnObject createClassifier(String businessName,String phoneNumber,Integer businessTypeId,String contactNo,MultipartFile file) ;

	ResultReturnList classifiedList(String contactNo);
	
	ResultReturnList classifiedListMobile();


	ResultReturnObject deleteClassifier(Integer classifierId,String contactNo) throws IOException;

	

	
	ResultReturnList filteredBusinessList(String startingLetter); 
	 }
	

