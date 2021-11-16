package com.church.barnabas.service;

import java.util.List;

import com.church.barnabas.dto.ResultReturnList;
import com.church.barnabas.dto.ResultReturnObject;
import com.church.barnabas.dto.UserListDto;
import com.church.barnabas.entity.ChurchProfile;
import com.church.barnabas.entity.UserTable;

public interface UserTableService {

	UserTable findByContactNumber(String contactnumber);
	
	UserTable createUser(UserTable userTable);
	
	ChurchProfile findFirst();
	
	ChurchProfile createOrupdate(ChurchProfile churchProfile);
	
	ResultReturnList userList(String contactNo);
	
	ResultReturnList userListMobile();

	ResultReturnObject updateUser(UserListDto userListDto);
	
	
	ResultReturnObject createUser(UserListDto userListDto);

	ResultReturnObject deleteUser(Integer userId);

	List<UserTable> getUserList();

}
