package com.church.barnabas.service;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.church.barnabas.dto.AnnouncementDTO;
import com.church.barnabas.dto.ResultReturnObject;
import com.church.barnabas.dto.UserAnnouncementReadDTO;

public interface AnnouncementService {

	ResultReturnObject createAnnouncement(AnnouncementDTO announcementDTO, MultipartFile file, String contactNo,
			HttpServletRequest request, Boolean fileExist) throws Exception;

	ResultReturnObject announcementList(String contactNo);

	ResultReturnObject announcementListMobile(String deviceId);

	ResultReturnObject announcementCount(String contactNo);

	ResultReturnObject announcementCountMobile();

	ResultReturnObject deleteAnnouncement(Integer announcementId) throws IOException;

	ResultReturnObject updateReadNotification(UserAnnouncementReadDTO userAnnouncementNotifyCountDTO);

	ResultReturnObject updatedCount(Integer id);
	
	

	ResultReturnObject totalUnreadNotificationCount(String deviceId);

//		

}
