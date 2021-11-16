package com.church.barnabas.service;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FilenameUtils;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.church.barnabas.dto.AnnouncementDTO;
import com.church.barnabas.dto.ResultReturnObject;
import com.church.barnabas.dto.UserAnnouncementReadDTO;
import com.church.barnabas.entity.UserAnnouncement;
import com.church.barnabas.entity.UserAnnouncementNotifyCount;
import com.church.barnabas.repo.NotificationRepo;
import com.church.barnabas.repo.UserAnnouncementNotifyRepo;
import com.church.barnabas.repo.UserAnnouncementRepo;
import com.church.barnabas.repo.UserRepo;

@Service
public class AnnouncementServiceImpl implements AnnouncementService {

	private static final Logger logger = Logger.getLogger(AnnouncementServiceImpl.class);
	@Autowired
	private UserAnnouncementRepo userAnnouncementRepo;
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private NotificationRepo notificationRepo;

	@Autowired
	private PushNotificationService pushNotificationService;
	@Autowired
	private UserAnnouncementNotifyRepo userAnnouncementNotifyRepo;
	private ListIterator<Integer> itr;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public ResultReturnObject createAnnouncement(AnnouncementDTO announcementDTO, MultipartFile file, String contactNo,
			HttpServletRequest request, Boolean fileExist) throws Exception {

		UserAnnouncement announceMent = userAnnouncementRepo.findByTitle(announcementDTO.getTitle());

//		if (announcementDTO.getTitle().equals(announceMent.getTitle())) {
//			return new ResultReturnObject(false,"Title Already Exist!!","");
//		}else {

		UserAnnouncement userAnnounce = new UserAnnouncement();

		userAnnounce.setTitle(announcementDTO.getTitle());
		userAnnounce.setDescription(announcementDTO.getDescription());
		userAnnounce.setUserTable(userRepo.findByContactNo(contactNo));
		userAnnounce.setStatus(announcementDTO.isStatus());

		String fileName = "";

		if (fileExist.equals(true)) {
			if (!file.isEmpty() && !file.equals(null) && !file.equals("")) {
				String ext = FilenameUtils.getExtension(file.getOriginalFilename());

				userAnnounce = userAnnouncementRepo.save(userAnnounce);
				fileName = userAnnounce.getId() + "." + ext;
				userAnnounce.setFilename(fileName);

				File f = new File(System.getProperty("user.dir") + File.separator + "BARNABAS" + File.separator
						+ "ANNOUNCEMENTS" + File.separator + fileName);
				if (!f.exists())
//			f.getParentFile().mkdirs(); 
					f.mkdirs();
				f.createNewFile();
				file.transferTo(f);
			}
		} else {
			userAnnounce = userAnnouncementRepo.save(userAnnounce);
		}

		Map<String, String> map = new HashMap<String, String>();
		map.put("title", announcementDTO.getTitle());
		map.put("description", announcementDTO.getDescription());
		map.put("imageurl", "/getannouncementImage?announcementid=" + userAnnounce.getId() + "&fileName=" + fileName);
		// pushNotificationService.sendNotification(new Note("Announcement",
		// announcementDTO.getTitle(), map, ""), "barnabas");

		return new ResultReturnObject(true, "Successfully Announcement is created!!", "");
//		}
	}

	@Override
	public ResultReturnObject announcementList(String contactNo) {
		return new ResultReturnObject(true, "Announcement Listed Successfully!!", userAnnouncementRepo.findAll());
	}

	@Override
	public ResultReturnObject announcementListMobile(String deviceId) {
		List<UserAnnouncementNotifyCount> announcementDetailsRead = userAnnouncementNotifyRepo
				.deviceIdDetails(deviceId);
		List<Integer> announcementIdList = announcementDetailsRead.stream()
				.map((UserAnnouncementNotifyCount count) -> count.getUserAnnouncement().getId())
				.collect(Collectors.toList());
		List<UserAnnouncement> userAnnouncementDetails = userAnnouncementRepo.findAll();
		String resultMessage = "No Announcement to display";
		for (UserAnnouncement userDetails : userAnnouncementDetails) {
			if (announcementIdList.contains(userDetails.getId())) {
				userDetails.setReadStatus(true);
			}
			resultMessage = "Announcement Listed Successfully";
		}
		return new ResultReturnObject(true, resultMessage, userAnnouncementDetails);
	}

	@Override
	public ResultReturnObject announcementCount(String contactNo) {
		return new ResultReturnObject(true, "Announcement Listed Successfully!!", userAnnouncementRepo.count());
	}

	@Override
	public ResultReturnObject announcementCountMobile() {
		return new ResultReturnObject(true, "Announcement Listed Successfully!!", userAnnouncementRepo.count());

	}

	public ResultReturnObject updatedCount(Integer id) {

		return new ResultReturnObject(true, "Announcement Listed Successfully!!",
				userAnnouncementRepo.updatedNewCount(id));

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = IOException.class)

	public ResultReturnObject deleteAnnouncement(Integer announcementId) throws IOException {
		userAnnouncementRepo.delete(userAnnouncementRepo.findById(announcementId).get());
		File f = new File(System.getProperty("user.dir") + File.separator + "BARNABAS" + File.separator
				+ "ANNOUNCEMENTS" + File.separator + announcementId);
		FileUtils.deleteDirectory(f);
		return new ResultReturnObject(true, "Announcement deleted Successfully!!", "");
	}

	@Override
	public ResultReturnObject totalUnreadNotificationCount(String deviceId) {

		int readCount = notificationRepo.readAnnouncementCount(deviceId);
		int totalNotification = userAnnouncementRepo.totalAnnouncementCount();
		int unreadCount = (totalNotification - readCount);

		return new ResultReturnObject(true, "Announcement unread count!", unreadCount);
	}

	public ResultReturnObject updateReadNotification(UserAnnouncementReadDTO userAnnounementRead) {

		List<UserAnnouncementNotifyCount> readvalues = userAnnouncementNotifyRepo
				.ReadCount(userAnnounementRead.getDeviceId(), userAnnounementRead.getAnnouncementId());
		if (readvalues == null) {
			UserAnnouncementNotifyCount notify = new UserAnnouncementNotifyCount();
			UserAnnouncement announcement = new UserAnnouncement();
			notify.setDeviceId(userAnnounementRead.getDeviceId());
			announcement.setId(userAnnounementRead.getAnnouncementId());
			notify.setUserAnnouncement(announcement);
			notify.setStatus(true);

			notificationRepo.save(notify);
		}
		return new ResultReturnObject(true, "User Announcement Read", "");
	}
	
//	public void cronDelete() {
//		List<String> id = userAnnouncementRepo.cronSelect();
//		userAnnouncementRepo.cronValDelete(id);
//		
//		
//		System.out.print("welcome" + id);
//		
		
	}


