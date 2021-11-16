
package com.church.barnabas.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.MatrixParam;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.MatrixVariable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.sns.AmazonSNSClient;
import com.church.barnabas.dto.AnnouncementDTO;
import com.church.barnabas.dto.BusinessTypeDto;
import com.church.barnabas.dto.ConfessionDto;
import com.church.barnabas.dto.MassTimingDto;
import com.church.barnabas.dto.MyProfileDTO;
import com.church.barnabas.dto.ResultReturnList;
import com.church.barnabas.dto.ResultReturnObject;
import com.church.barnabas.dto.UserAnnouncementReadDTO;
import com.church.barnabas.dto.UserListDto;
import com.church.barnabas.entity.ChurchProfile;
import com.church.barnabas.entity.UserTable;
import com.church.barnabas.repo.UserAnnouncementRepo;
import com.church.barnabas.service.AnnouncementService;
import com.church.barnabas.service.AnnouncementServiceImpl;
import com.church.barnabas.service.BannerService;
import com.church.barnabas.service.ClassifierService;
import com.church.barnabas.service.ConfessionService;
import com.church.barnabas.service.MassTimingService;
import com.church.barnabas.service.UserTableService;
import com.google.firebase.database.annotations.Nullable;

import io.swagger.annotations.ApiOperation;
import lombok.NonNull;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@CrossOrigin("*")
public class RestControllerChruch {
	private static final Logger logger = Logger.getLogger(RestControllerChruch.class);
	private static final String[] value = null;
	@Autowired
	private AmazonSNSClient amazonSNSClient;
	@Autowired
	private UserTableService userService;
	@Autowired
	private AnnouncementService announcementService;
	@Autowired
	private BannerService bannerService;
	@Autowired
	private MassTimingService massTimingService;
	@Autowired
	private ConfessionService confessionService;
	@Autowired
	private ClassifierService classifierService;
	@Autowired
	private UserAnnouncementRepo userAnnouncementRepo;
	
	@Autowired
	private AnnouncementServiceImpl announcementServiceImpl;

	public @NonNull String gen() {
		Random r = new Random(System.currentTimeMillis());
		return String.valueOf(10000 + r.nextInt(20000));
	}

	@RequestMapping(value = "/createAnnouncement", method = RequestMethod.POST, consumes = { "multipart/form-data" })
	@PreAuthorize("hasAuthority('Admin')")
	@ApiOperation(value = "Create or Update URL - {WEB & Android} {AUTH} {only ADMIN}", response = ResultReturnObject.class)
	public ResultReturnObject createAnnouncement(@ApiIgnore OAuth2Authentication oauth, @RequestParam String title,
			@RequestParam String description, @RequestParam Boolean fileExist,
			@RequestPart(name = "file", required = false) MultipartFile file, @ApiIgnore HttpServletRequest request)
			throws Exception {

		try {
			AnnouncementDTO announcementDTO = new AnnouncementDTO();

			announcementDTO.setTitle(title);
			announcementDTO.setDescription(description);
			announcementDTO.setStatus(true);
			return announcementService.createAnnouncement(announcementDTO, file, oauth.getName(), request, fileExist);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultReturnObject(false, "Something Went Wrong!!", "");
		}
	}


	

	@RequestMapping(value = "/getannouncementImage", method = RequestMethod.GET, produces = MediaType.ALL_VALUE)
	@ApiOperation(value = "get announcement image- {WEB & Android} ", response = ResultReturnObject.class)
	public ResponseEntity<InputStreamResource> getImage(@RequestParam Integer id) throws IOException {
		String fileName = userAnnouncementRepo.announcementDownload(id);
		File f = new File(System.getProperty("user.dir") + File.separator + "BARNABAS" + File.separator
				+ "ANNOUNCEMENTS" + File.separator + fileName);

		InputStreamResource resource = new InputStreamResource(new FileInputStream(f));
		 String mimeType = java.nio.file.Files.probeContentType(f.toPath());
		 logger.info("F name"+f.getName());
		 logger.info(mimeType);
		 //java.nio.file.Files.probeContentType(null)

	// return resource;
		return ResponseEntity.ok().contentLength(f.length()).contentType(MediaType.parseMediaType(mimeType))
				.header("Content-Disposition", "inline;filename=" + fileName).body(resource);

	}



	

	@RequestMapping(value = "/deleteAnnouncement", method = RequestMethod.POST)
	@PreAuthorize("hasAuthority('Admin')")
	@ApiOperation(value = "delete announcement - {WEB & Android} {AUTH} {only ADMIN}", response = ResultReturnObject.class)
	public ResultReturnObject deleteAnnouncement(@ApiIgnore OAuth2Authentication oauth,
			@RequestParam Integer announcementId, @ApiIgnore HttpServletRequest request) {
		try {
			return announcementService.deleteAnnouncement(announcementId);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultReturnObject(false, "Something Went Wrong!!", "");
		}
	}

	@GetMapping(value = "/myprofile")
	@ApiOperation(value = "Get Profile Data - {AUTH}", response = ResultReturnObject.class)
	public ResultReturnObject display(@ApiIgnore OAuth2Authentication oauth) {
		MyProfileDTO myprofile = new MyProfileDTO();
		myprofile.setContactNo(oauth.getName());
		String role = "";
		Iterator<GrantedAuthority> iterator = oauth.getAuthorities().iterator();
		while (iterator.hasNext()) {
			role = iterator.next().getAuthority();
		}
		System.out.println("oauth=" + oauth.getName() + "role=" + role);
		myprofile.setRoleName(role);
		myprofile.setChurchProfile(userService.findFirst());
		return new ResultReturnObject(true, "Myprofile API listed successfully!!", myprofile);
	}

	@GetMapping(value = "/myprofileMobile")
	@ApiOperation(value = "Get Profile Data - {AUTH}", response = ResultReturnObject.class)
	public ResultReturnObject myprofile() {
		MyProfileDTO myprofile = new MyProfileDTO();
		myprofile.setContactNo("");
		String role = "";
		myprofile.setRoleName(role);
		myprofile.setChurchProfile(userService.findFirst());
		return new ResultReturnObject(true, "Myprofile API listed successfully!!", myprofile);
	}

	@GetMapping("/getAnnouncementList")
	@ApiOperation(value = "Get URL List - {WEB & Android} {AUTH}", response = ResultReturnObject.class)
	public ResultReturnObject getAnnouncementList(@ApiIgnore OAuth2Authentication oauth) {
		return announcementService.announcementList(oauth.getName());
	}

	@GetMapping("/getAnnouncementListMobile")
	@ApiOperation(value = "Get URL List - { Android} ", response = ResultReturnObject.class)
	public ResultReturnObject getAnnouncementListMobile(@RequestParam String deviceId) {
		return announcementService.announcementListMobile(deviceId);
	}

	@GetMapping("/getAnnouncementCount")
	@ApiOperation(value = "Get URL List - {WEB & Android} {AUTH}", response = ResultReturnObject.class)
	public ResultReturnObject getAnnouncementCount(@ApiIgnore OAuth2Authentication oauth) {
		return announcementService.announcementCount(oauth.getName());
	}

	@GetMapping("/getAnnouncementCountMobile")
	@ApiOperation(value = "Get URL List - { Android} ", response = ResultReturnObject.class)
	public ResultReturnObject getAnnouncementCountMobile() {
		return announcementService.announcementCountMobile();
	}

	@GetMapping("/UpdatedAnnouncementCountMobile")
	@ApiOperation(value = "Get URL List - { Android}", response = ResultReturnObject.class)
	public ResultReturnObject updatedAnnouncementCountMobile(@RequestParam Integer id) {

		return announcementService.updatedCount(id);
	}

	@GetMapping("/UnReadNotificationCount")
	@ApiOperation(value = "Get URL List -  {Android}", response = ResultReturnObject.class)
	public ResultReturnObject UpdateReadCountMobile(@RequestParam String deviceId) {
		return announcementService.totalUnreadNotificationCount(deviceId);
	}

	@PostMapping("/ReadNotificationsbyDeviceId")
	@ApiOperation(value = "Update Announcement Read - {Android}", response = ResultReturnObject.class)
	public ResultReturnObject updateReadNotification(@RequestBody UserAnnouncementReadDTO userAnnouncmentRead) {
		return announcementService.updateReadNotification(userAnnouncmentRead);
	}

	@PostMapping("/createOrUpdateAllurl")
	@PreAuthorize("hasAuthority('Admin')")
	@ApiOperation(value = "Create or Update URL - {WEB} {AUTH} {only ADMIN}", response = ResultReturnObject.class)
	public ResultReturnObject createOrUpdateURL(@RequestBody ChurchProfile churchProfile) {
		userService.createOrupdate(churchProfile);
		return new ResultReturnObject(true, "profile updated successfully!!", "");
	}

	@PostMapping(value = "/sendNotification")

	@ApiOperation(value = "Send OTP SMS to users -app can be either WEB or MOBILE - {NO AUTH}", response = ResultReturnObject.class)

	public ResultReturnObject publishMessageToTopic(@RequestParam String contactNumber, @RequestParam String app) {

		if (app.equalsIgnoreCase("MOBILE")) {

//			UserTable ut = userService.findByContactNumber(contactNumber);
//
//			String otp = gen();

//			if (ut == null || ut.getRoleName().equalsIgnoreCase("Contributor")) {

//				if (ut == null)
//
//					ut = new UserTable();
//
//				ut.setContactNo(contactNumber);
//
//				ut.setOtp("");
//
//				ut.setRoleName("Contributor");
//				userService.createUser(ut);
//
//
//			} else {
//
//				Date dNow = new Date(); // Instantiate a Date object
//
//				Calendar cal = Calendar.getInstance();
//
//				cal.setTime(dNow);
//
//				cal.add(Calendar.MINUTE, 2);
//
//				ut.setOtp(otp);
//
//				System.out.println("otp...."+otp);
//				
//				ut.setOtpExpiry(new Timestamp(cal.getTimeInMillis()));
//
//			}
//
//
//			if (ut.getRoleName().equals("Admin")) {

//				Notification notification = new Notification(otp, contactNumber);
//
//				final PublishRequest publishRequest = new PublishRequest();
//
//				publishRequest.setPhoneNumber(notification.getPhonenumber());
//
//				publishRequest.setMessage(notification.getMessage());
//
//				try {
//
//					PublishResult pr = amazonSNSClient.publish(publishRequest);
//
//					System.out.println(pr.getSdkResponseMetadata().toString());
//					System.out.println("otp...."+otp);
//
//				} catch (Exception e) {
//
//					return new ResultReturnObject(false, "Something Went Wrong!!!", " ");
//
//				}

//				return new ResultReturnObject(true, "OTP sent successfully!!", "Admin");
//
//			} else {
//
//				return new ResultReturnObject(true, "Contributor Login successfully!!", "Contributor");
//
//			}
			return new ResultReturnObject(true, "Contributor Login successfully!!", "Contributor");

		} else if (app.equalsIgnoreCase("WEB")) {

			UserTable ut = userService.findByContactNumber(contactNumber);

//			if (ut == null || !ut.getRoleName().equals("Admin")) {
			if (ut == null) {
				return new ResultReturnObject(false, "Please enter valid contact number of admin user", "");

			}

			String otp = gen();

			Date dNow = new Date(); // Instantiate a Date object

			Calendar cal = Calendar.getInstance();

			cal.setTime(dNow);

			cal.add(Calendar.MINUTE, 2);

//			ut.setOtp(otp);
//			ut.setOtpExpiry(new Timestamp(cal.getTimeInMillis()));
//			userService.createUser(ut);
//			
//			Notification notification = new Notification(otp, contactNumber);
//			final PublishRequest publishRequest = new PublishRequest();
//			publishRequest.setPhoneNumber(notification.getPhonenumber());
//			publishRequest.setMessage(notification.getMessage());
//
//			try {
//
//				PublishResult pr = amazonSNSClient.publish(publishRequest);
//
//				System.out.println(pr.getSdkResponseMetadata().toString());
//				System.out.println("otp...."+otp);
//
//			} catch (Exception e) {
//
//				return new ResultReturnObject(false, "Something Went Wrong!!!", " ");
//
//			}

			return new ResultReturnObject(true, "Admin Login successfully!!", "Admin");

		} else {

			return new ResultReturnObject(false, "Please pass correct app name", "");
		}

	}

	@RequestMapping(value = "/uploadBannerImage", method = RequestMethod.POST, consumes = { "multipart/form-data" })
	@PreAuthorize("hasAuthority('Admin')")
	@ApiOperation(value = "Upload Banner Image - {WEB & Android} {AUTH} {only ADMIN}", response = ResultReturnObject.class, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResultReturnObject uploadBannerImage(@ApiIgnore OAuth2Authentication oauth,
			@RequestPart(value = "file", required = true) MultipartFile file) {
		return bannerService.uploadBannerImage(file, oauth.getName());
	}

	@RequestMapping(value = "/deleteBannerImage", method = RequestMethod.POST)
	@PreAuthorize("hasAuthority('Admin')")
	@ApiOperation(value = "Delete Banner Image - {WEB & Android} {AUTH} {only ADMIN}", response = ResultReturnObject.class)
	public ResultReturnObject deleteBannerImage(@ApiIgnore OAuth2Authentication oauth, @RequestParam String fileName)
			throws FileNotFoundException {
		return bannerService.deleteBannerImage(oauth.getName(), fileName);

	}

	@GetMapping("/bannerImageList")
	@ApiOperation(value = "bannerImageList - {WEB & Android} {AUTH}", response = ResultReturnObject.class)
	public ResultReturnList bannerImageList(@ApiIgnore OAuth2Authentication oauth) {
		return bannerService.bannerImageList(oauth.getName());
	}

	@GetMapping("/bannerImageListMobile")
	@ApiOperation(value = "bannerImageListMobile - {Android} ", response = ResultReturnObject.class)
	public ResultReturnList bannerImageListMobile() {
		return bannerService.bannerImageListMobile();
	}

	@RequestMapping(value = "/downloadBannerImage", method = RequestMethod.GET/*
																				 * ,consumes=MediaType. *
																				 * APPLICATION_JSON_VALUE
																				 */)
	@ApiOperation(value = "download Banner Image - {WEB & Android} {AUTH}", response = ResultReturnObject.class)
	public ResponseEntity<InputStreamResource> downloadBannerImage(@RequestParam("fileName") String fileName)
			throws Exception {
		return bannerService.downloadBannerImage(fileName);

	}

	@RequestMapping(value = "/download", method = RequestMethod.GET)
	@ApiOperation(value = "download - {WEB & Android} {AUTH}", response = ResultReturnObject.class)
	public ResponseEntity<InputStreamResource> download(@RequestParam("path") String path) throws Exception {
		return bannerService.download(path);

	}

	@RequestMapping(value = "/createMassTiming", method = RequestMethod.POST)
	@PreAuthorize("hasAuthority('Admin')")
	@ApiOperation(value = "createMassTiming - {WEB & Android} {AUTH} {only ADMIN}", response = ResultReturnObject.class)
	public ResultReturnObject createMassTiming(@ApiIgnore OAuth2Authentication oauth,
			@RequestBody MassTimingDto massTimingDto) throws ParseException {
		return massTimingService.createMassTiming(massTimingDto, oauth.getName());
	}

	@RequestMapping(value = "/updateMassTiming", method = RequestMethod.POST)
	@PreAuthorize("hasAuthority('Admin')")
	@ApiOperation(value = "updateMassTiming - {WEB & Android} {AUTH} {only ADMIN}", response = ResultReturnObject.class)
	public ResultReturnObject updateMassTiming(@ApiIgnore OAuth2Authentication oauth,
			@RequestBody MassTimingDto massTimingDto,@RequestParam Integer id) {
		return massTimingService.updateMassTiming(massTimingDto, oauth.getName(), id);
	}

	@GetMapping("/massTimingList")
	@ApiOperation(value = "massTimingList - {WEB & Android} {AUTH}", response = ResultReturnObject.class)
	public ResultReturnObject massTimingList(@ApiIgnore OAuth2Authentication oauth) {
		return massTimingService.massTimingList(oauth.getName());
	}

	@GetMapping("/massTimingListMobile")
	@ApiOperation(value = "massTimingListMobile - { Android}", response = ResultReturnObject.class)
	public ResultReturnObject massTimingListMobile() {
		return massTimingService.massTimingListMobile();
	}

	@RequestMapping(value = "/deleteMassTiming", method = RequestMethod.POST)
	@PreAuthorize("hasAuthority('Admin')")
	@ApiOperation(value = "deleteMassTiming - {WEB & Android} {AUTH} {only ADMIN}", response = ResultReturnObject.class)
	public ResultReturnObject deleteMassTiming(@ApiIgnore OAuth2Authentication oauth,
			@RequestParam("massTimingId") Integer massTimingId) {
		return massTimingService.deleteMassTiming(massTimingId, oauth.getName());
	}

	@RequestMapping(value = "/createConfession", method = RequestMethod.POST)
	@PreAuthorize("hasAuthority('Admin')")
	@ApiOperation(value = "createConfession - {WEB & Android} {AUTH} {only ADMIN}", response = ResultReturnObject.class)
	ResultReturnObject createConfession(@ApiIgnore OAuth2Authentication oauth,
			@RequestBody ConfessionDto confessionDto) {
		return confessionService.createConfession(oauth.getName(), confessionDto);
	}

	@GetMapping("/confessionList")
	@ApiOperation(value = "confessionList - {WEB & Android} {AUTH}", response = ResultReturnObject.class)
	public ResultReturnObject confessionList(@ApiIgnore OAuth2Authentication oauth) {
		return confessionService.confessionList(oauth.getName());
	}

	@RequestMapping(value = "/scheduleConfession", method = RequestMethod.POST)
	@ApiOperation(value = "scheduleConfession - {WEB & Android} {AUTH}", response = ResultReturnObject.class)
	public ResultReturnObject scheduleConfession(@ApiIgnore OAuth2Authentication oauth) {
		return confessionService.scheduleConfession(oauth.getName());
	}

	@GetMapping("/availableConfessionSlots")
	@ApiOperation(value = "availableConfessionSlots - {WEB & Android} {AUTH}", response = ResultReturnObject.class)
	public ResultReturnObject availableConfessionSlots(@ApiIgnore OAuth2Authentication oauth) throws ParseException {
		return confessionService.availableConfessionSlots(oauth.getName());
	}

	@RequestMapping(value = "/createBusinessType", method = RequestMethod.POST)
	@PreAuthorize("hasAuthority('Admin')")
	@ApiOperation(value = "createBusinessType - {WEB & Android} {AUTH} {only ADMIN}", response = ResultReturnObject.class)
	ResultReturnObject createBusinessType(@ApiIgnore OAuth2Authentication oauth,
			@RequestBody BusinessTypeDto businessTypeDto) {
		return classifierService.createBusinessType(businessTypeDto, oauth.getName());
	}

	@GetMapping("/businessTypeList")
	@ApiOperation(value = "businessTypeList - {WEB & Android} {AUTH}", response = ResultReturnObject.class)
	public ResultReturnList businessTypeList(@ApiIgnore OAuth2Authentication oauth,
			@RequestParam("search") String search) {
		return classifierService.businessTypeList(oauth.getName(), search);
	}
    
	@GetMapping("/specificClassified")
	@ApiOperation(value = "businessTypeList - {Android}", response = ResultReturnObject.class)
	public ResultReturnList filteredBusinessType(@org.springframework.lang.Nullable @RequestParam(value = "startingLetter",required = false) String startingLetter) {
		return classifierService.filteredBusinessList(startingLetter);
	}
	
	
	@RequestMapping(value = "/deleteBusinessType", method = RequestMethod.POST)
	@PreAuthorize("hasAuthority('Admin')")
	@ApiOperation(value = "deleteBusinessType - {WEB & Android} {AUTH} {only ADMIN}", response = ResultReturnObject.class)
	ResultReturnObject deleteBusinessType(@ApiIgnore OAuth2Authentication oauth,
			@RequestParam("businessTypeId") Integer businessTypeId) {
		return classifierService.deleteBusinessType(businessTypeId, oauth.getName());
	}

	@RequestMapping(value = "/createClassifier", method = RequestMethod.POST)
	@PreAuthorize("hasAuthority('Admin')")
	@ApiOperation(value = "createClassifier - {WEB & Android} {AUTH} {only ADMIN}", response = ResultReturnObject.class)
	ResultReturnObject createClassifier(@ApiIgnore OAuth2Authentication oauth,
			@RequestParam("businessName") String businessName, @RequestParam("phoneNumber") String phoneNumber,
			@RequestParam("businessTypeId") Integer businessTypeId,
			@RequestPart(name = "file", required = false) MultipartFile file) {
		return classifierService.createClassifier(businessName, phoneNumber, businessTypeId, oauth.getName(), file);
	}

	@GetMapping("/classifiedList")
	@ApiOperation(value = "classifiedList - {WEB & Android} {AUTH}", response = ResultReturnObject.class)
	public ResultReturnList classifiedList(@ApiIgnore OAuth2Authentication oauth) {
		return classifierService.classifiedList(oauth.getName());
	}

	@GetMapping("/classifiedListMobile")
	@ApiOperation(value = "classifiedListMobile - { Android}", response = ResultReturnObject.class)
	public ResultReturnList classifiedListMobile() {
		return classifierService.classifiedListMobile();
	}

	@RequestMapping(value = "/deleteClassifier", method = RequestMethod.POST)
	@PreAuthorize("hasAuthority('Admin')")
	@ApiOperation(value = "deleteClassifier - {WEB & Android} {AUTH} {only ADMIN}", response = ResultReturnObject.class)
	ResultReturnObject deleteClassifier(@ApiIgnore OAuth2Authentication oauth,
			@RequestParam("classifierId") Integer classifierId) throws IOException {
		return classifierService.deleteClassifier(classifierId, oauth.getName());
	}

	@GetMapping("/userList")
	@ApiOperation(value = "userList - {WEB & Android} {AUTH}", response = ResultReturnObject.class)
	public ResultReturnList userList(@ApiIgnore OAuth2Authentication oauth) {
		return userService.userList(oauth.getName());
	}

	@GetMapping("/userListMobile")
	@ApiOperation(value = "userListMobile - { Android} ", response = ResultReturnObject.class)
	public ResultReturnList userListMobile() {
		return userService.userListMobile();
	}

	@RequestMapping(value = "/updateUser", method = RequestMethod.POST)
	@ApiOperation(value = "updateUser - {WEB & Android} {AUTH}", response = ResultReturnObject.class)
	ResultReturnObject updateUser(@ApiIgnore OAuth2Authentication oauth, @RequestBody UserListDto userListDto) {
		return userService.updateUser(userListDto);
	}

	@RequestMapping(value = "/updateUserMobile", method = RequestMethod.POST)
	@ApiOperation(value = "updateUserMobile - { Android}", response = ResultReturnObject.class)
	ResultReturnObject updateUserMobile(@RequestBody UserListDto userListDto) {
		return userService.updateUser(userListDto);
	}

	@RequestMapping(value = "/createUser", method = RequestMethod.POST)
	@ApiOperation(value = "createUser - {WEB & Android} {AUTH}", response = ResultReturnObject.class)
	public ResultReturnObject createUser(@ApiIgnore OAuth2Authentication oauth, @RequestBody UserListDto userListDto) {
		return userService.createUser(userListDto);
	}

	@RequestMapping(value = "/createUserMobile", method = RequestMethod.POST)
	@ApiOperation(value = "createUserMobile - { Android} ", response = ResultReturnObject.class)
	public ResultReturnObject createUserMobile(@RequestBody UserListDto userListDto) {
		return userService.createUser(userListDto);
	}

	@RequestMapping(value = "/deleteUser", method = RequestMethod.POST)
	@PreAuthorize("hasAuthority('Admin')")
	@ApiOperation(value = "deleteUser - {WEB & Android} {AUTH} {only ADMIN}", response = ResultReturnObject.class)
	public ResultReturnObject deleteUser(@ApiIgnore OAuth2Authentication oauth,
			@RequestParam("userId") Integer userId) {
		return userService.deleteUser(userId);
	}
	
//	@GetMapping(value="getCronval")
//	@ApiOperation(value = "deleteUser - {WEB & Android} {AUTH} {only ADMIN}")
//	public void cronDelete() {
//		announcementServiceImpl.cronDelete();
	
	@RequestMapping(value = "/getList",method = RequestMethod.GET)
		public List<UserTable> getList(){
			return userService.getUserList();
		}
	
		
	}
	

