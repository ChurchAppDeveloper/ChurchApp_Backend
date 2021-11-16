package com.church.barnabas.service;

import java.io.FileNotFoundException;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.church.barnabas.dto.ResultReturnList;
import com.church.barnabas.dto.ResultReturnObject;

public interface BannerService {
	
	ResultReturnObject uploadBannerImage(MultipartFile file,String contactNo);
	
	ResultReturnList bannerImageList(String contactNo);
	
	ResultReturnList bannerImageListMobile();

	ResultReturnObject deleteBannerImage(String contactNo,String fileName) throws FileNotFoundException;

	 ResponseEntity<InputStreamResource> download(String path) throws Exception;
     
     public ResponseEntity<InputStreamResource> downloadBannerImage(String fileName) throws Exception;
     
}
