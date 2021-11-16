package com.church.barnabas.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.services.s3.model.PublicAccessBlockConfiguration;
import com.church.barnabas.dto.ResultReturnList;
import com.church.barnabas.dto.ResultReturnObject;
import com.church.barnabas.dto.UserListDto;
import com.church.barnabas.entity.ChurchProfile;
import com.church.barnabas.entity.UserTable;
import com.church.barnabas.repo.ChurchProfileRepo;
import com.church.barnabas.repo.UserRepo;

@Service
public class UserTableServiceImpl implements UserTableService {

	@Autowired
	private UserRepo userRepo;
	@Autowired
	private ChurchProfileRepo churchProfileRepo;
	
	@Override
	public UserTable findByContactNumber(String contactnumber) {
		
		return userRepo.findByContactNo(contactnumber);
	}

	@Override
	public UserTable createUser(UserTable userTable) {
		return userRepo.save(userTable);
	}

	@Override
	public ChurchProfile findFirst() {	
		return churchProfileRepo.findTopByOrderByIdDesc();
	}

	@Override
	public ChurchProfile createOrupdate(ChurchProfile churchProfile) {
		churchProfile.setId(1);
		return churchProfileRepo.save(churchProfile);
	}

	@Override
	public ResultReturnList userList(String contactNo) {
		
		List<UserListDto> userList=new ArrayList<UserListDto>();
		
	List<UserTable> users=userRepo.findAll();
	for(UserTable user: users){
		UserListDto userListDto=new UserListDto();
		
		userListDto.setId(user.getId());
		userListDto.setName(user.getName());
		userListDto.setEmail(user.getEmail());
		userListDto.setAddress(user.getAddress());
		userListDto.setContactNo(user.getContactNo());
		userListDto.setActiveStatus(user.getActiveStatus());
		userList.add(userListDto);
	}

	return new ResultReturnList(true,"Successfully Users are Listed!!",userList);
	}

	@Override
	public ResultReturnList userListMobile() {
		
		List<UserListDto> userList=new ArrayList<UserListDto>();
		
	List<UserTable> users=userRepo.findAll();
	for(UserTable user: users){
		UserListDto userListDto=new UserListDto();
		
		userListDto.setId(user.getId());
		userListDto.setName(user.getName());
		userListDto.setEmail(user.getEmail());
		userListDto.setAddress(user.getAddress());
		userListDto.setContactNo(user.getContactNo());
		userListDto.setActiveStatus(user.getActiveStatus());
		userList.add(userListDto);
	}

	return new ResultReturnList(true,"Successfully Users are Listed!!",userList);
	}
	
	
	@Override
	public ResultReturnObject updateUser(UserListDto userListDto) {
		
		UserTable user=userRepo.getOne(userListDto.getId());
		if(user==null) {
			return new ResultReturnObject(false,"User Not Exist!!","");	
		}else {
		
		user.setName(userListDto.getName());
		user.setEmail(userListDto.getEmail());
		user.setAddress(userListDto.getAddress());
		user.setContactNo(userListDto.getContactNo());
		user.setActiveStatus(userListDto.getActiveStatus());
		if ( userListDto.getPassword()!=null && userListDto.getPassword() !="" ) {
			user.setOtp(userListDto.getPassword());
		}
		userRepo.save(user);
		return new ResultReturnObject(true,"Successfully User  updated!!","");

		}
	}

	@Override
	public ResultReturnObject createUser(UserListDto userListDto) {

		
		UserTable userExist=userRepo.findByContactNo(userListDto.getContactNo());
		
		if(userExist!=null) {
			return new ResultReturnObject(false,"User already Exist!!","");	
		}else {
		UserTable user=new UserTable();
		user.setName(userListDto.getName());
		user.setEmail(userListDto.getEmail());
		user.setAddress(userListDto.getAddress());
		user.setContactNo(userListDto.getContactNo());
		user.setActiveStatus(true);
		if ( userListDto.getPassword()!=null && userListDto.getPassword() !="" ) {
			user.setOtp(userListDto.getPassword());
		}
		
		userRepo.save(user);
		return new ResultReturnObject(true,"Successfully User  Created!!","");

		}
	}

	@Override
	public ResultReturnObject deleteUser(Integer userId) {
     UserTable userExist=userRepo.findById(userId).get();
		
		if(userExist==null) {
			return new ResultReturnObject(false,"User Not Exist!!","");	
		}else {
			userExist.setActiveStatus(false);
			userRepo.save(userExist);

			return new ResultReturnObject(true,"Successfully User  deleted!!","");	
	}
	
		
	

}
	public List<UserTable> getUserList() {
		return userRepo.findAll();
	}
}

