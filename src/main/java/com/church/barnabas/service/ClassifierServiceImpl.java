package com.church.barnabas.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.web.multipart.MultipartFile;

import com.church.barnabas.dto.BusinessTypeDto;
import com.church.barnabas.dto.ClassifierDto;
import com.church.barnabas.dto.ResultReturnList;
import com.church.barnabas.dto.ResultReturnObject;
import com.church.barnabas.entity.BusinessTypes;
import com.church.barnabas.entity.Classified;
import com.church.barnabas.repo.BusinessTypeRepo;
import com.church.barnabas.repo.ClassifierRepo;
import com.church.barnabas.repo.UserRepo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Service
public class ClassifierServiceImpl implements ClassifierService {

	@Autowired
	private BusinessTypeRepo businessTypeRepo;

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private ClassifierRepo classifierRepo;

	@Override
	public ResultReturnObject createBusinessType(BusinessTypeDto businessTypeDto, String contactNo) {

		BusinessTypes businessName = businessTypeRepo.findByBusinessName(businessTypeDto.getBusinessName());
		if (businessName != null) {
			return new ResultReturnObject(false, "BusinessName already Exist!!", "");
		} else {

			BusinessTypes businessTypes = new BusinessTypes();
			businessTypes.setBusinessName(businessTypeDto.getBusinessName());
			businessTypeRepo.save(businessTypes);
		}
		return new ResultReturnObject(true, "Successfully BusinessType is created!!", "");
	}

	@Override
	public ResultReturnList businessTypeList(String contactNo, String search) {

		List<BusinessTypeDto> businessTypeDtoList = new ArrayList<BusinessTypeDto>();
		List<BusinessTypes> businessTypeList = businessTypeRepo.searchByBusinessTypeName(search);

		for (BusinessTypes businessTypes : businessTypeList) {
			BusinessTypeDto businessTypeDto = new BusinessTypeDto();
			businessTypeDto.setBusinessId(businessTypes.getBusinessId());
			businessTypeDto.setBusinessName(businessTypes.getBusinessName());
			businessTypeDtoList.add(businessTypeDto);

		}
		return new ResultReturnList(true, "Successfully BusinessType is Listed!!", businessTypeDtoList);
	}

	@Override
	public ResultReturnObject deleteBusinessType(Integer businessTypeId, String contactNo) {

		BusinessTypes businessType = businessTypeRepo.findById(businessTypeId).get();

		businessTypeRepo.delete(businessType);

		return new ResultReturnObject(true, "Successfully BusinessType is deleted!!", "");
	}

	@Override
	public ResultReturnObject createClassifier(String businessName, String phoneNumber, Integer businesstypeId,
			String contactNo, MultipartFile file) {

		try {
			Classified classified = new Classified();

			classified.setName(businessName);
			classified.setPhoneNumber(phoneNumber);
			classified.setBusinessTypes(businessTypeRepo.findById(businesstypeId).get());
			classified.setUserTable(userRepo.findByContactNo(contactNo));
			classified.setBusinessName(classifierRepo.getBusinessName(businesstypeId));

			File path = new File(System.getProperty("user.dir") + File.separator + "Classifier");
			if (!path.exists()) {
				path.mkdirs();

			}
			String ext = FilenameUtils.getExtension(file.getOriginalFilename());
			String fname = String.valueOf(System.currentTimeMillis()) + "." + ext;
			String fileName = path + File.separator + fname;

			if (!file.isEmpty()) {
				File f = new File(fileName);

				file.transferTo(f);
			}

			classified.setImagename(fname);
			classifierRepo.save(classified);

		} catch (Exception e) {
			e.printStackTrace();
			return new ResultReturnObject(false, "Something Went Wrong!!", "");
		}

		return new ResultReturnObject(true, "Successfully Classifier is created!!", "");
	}

	@Override
	public ResultReturnList classifiedList(String contactNo) {

		List<ClassifierDto> classifierDtoList = new ArrayList<ClassifierDto>();
		List<Classified> classifiedList = classifierRepo.findAll();
		for (Classified classified : classifiedList) {

			ClassifierDto classifierDto = new ClassifierDto();

			classifierDto.setId(classified.getId());
			classifierDto.setBusinessName(classified.getName());
			classifierDto.setPhoneNumber(classified.getPhoneNumber());
			classifierDto.setBusinessTypeId(classified.getBusinessTypes().getBusinessId());
			classifierDto.setBusinessTypeName(classified.getBusinessTypes().getBusinessName());

//			 String path=System.getProperty("user.dir")+File.separator+"Classifier"+File.separator+classified.getImagename();
			String path = "/download?path=" + classified.getImagename();
			classifierDto.setImagename(path);

			classifierDtoList.add(classifierDto);
		}

		return new ResultReturnList(true, "Successfully Classifier is Listed!!", classifierDtoList);
	}

	@Override
	public ResultReturnList classifiedListMobile() {
		List<ClassifierDto> classifierDtoList = new ArrayList<ClassifierDto>();
		List<Classified> classifiedList = classifierRepo.findAll();
		for (Classified classified : classifiedList) {

			ClassifierDto classifierDto = new ClassifierDto();

			classifierDto.setId(classified.getId());
			classifierDto.setBusinessName(classified.getName());
			classifierDto.setPhoneNumber(classified.getPhoneNumber());
			classifierDto.setBusinessTypeId(classified.getBusinessTypes().getBusinessId());
			classifierDto.setBusinessTypeName(classified.getBusinessTypes().getBusinessName());

//			 String path=System.getProperty("user.dir")+File.separator+"Classifier"+File.separator+classified.getImagename();
			String path = "/download?path=" + classified.getImagename();
			classifierDto.setImagename(path);

			classifierDtoList.add(classifierDto);
		}

		return new ResultReturnList(true, "Successfully Classifier is Listed!!", classifierDtoList);
	}

	public ResultReturnList filteredBusinessList(String startingLetter) {
		List<Classified> classified = new ArrayList<Classified>();
		if (startingLetter == null) {
			classified=classifierRepo.findAll();

		} else {
			classified=classifierRepo.getValuesOnLetters(startingLetter);
		}

		return new ResultReturnList(true, "Success",classified);

	}

	@Override
	public ResultReturnObject deleteClassifier(Integer classifierId, String contactNo) throws IOException {

		System.out.println("classifierId" + classifierId);
		Classified classified = classifierRepo.findById(classifierId).get();
		if (classified == null) {
			return new ResultReturnObject(false, "Classifier Not Exist!!", "");
		} else {
			classifierRepo.delete(classified);

			File f = new File(System.getProperty("user.dir") + File.separator + "Classifier" + File.separator
					+ classified.getImagename());
			f.delete();
			return new ResultReturnObject(true, "Classified deleted Successfully!!", "");
		}

	}

}
