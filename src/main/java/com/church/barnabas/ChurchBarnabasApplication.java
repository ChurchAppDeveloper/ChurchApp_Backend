package com.church.barnabas;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

import com.church.barnabas.controller.RestControllerChruch;
import com.church.barnabas.repo.UserAnnouncementRepo;
import com.church.barnabas.service.AnnouncementServiceImpl;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;

@SpringBootApplication
@EnableAuthorizationServer
@EnableResourceServer
@EnableEurekaClient
@EnableAsync
@EnableScheduling
public class ChurchBarnabasApplication {

	private static final Logger logger = Logger.getLogger(ChurchBarnabasApplication.class);

	@Autowired
	private UserAnnouncementRepo userAnnouncementRepo;
    
	private AnnouncementServiceImpl announcementServiceImpl;
	public static void main(String[] args) {
		RestControllerChruch rc = new RestControllerChruch();
		System.out.println(rc.gen());
		SpringApplication.run(ChurchBarnabasApplication.class, args);
	}

	@Bean
	FirebaseMessaging firebaseMessaging() throws IOException {
		GoogleCredentials googleCredentials = GoogleCredentials.fromStream(
				new ClassPathResource("barnabas-a9438-firebase-adminsdk-omrfj-d6d9e2b360.json").getInputStream());
		FirebaseOptions firebaseOptions = FirebaseOptions.builder().setCredentials(googleCredentials).build();
		FirebaseApp app = FirebaseApp.initializeApp(firebaseOptions, "my-app");
		return FirebaseMessaging.getInstance(app);
	}

	@Scheduled(cron = "0 0 0 * * ?")
	public void resetCache() {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date date = new Date();
		System.out.println(formatter.format(date));
		logger.info("Cron job running " + date);
		List<String> id = userAnnouncementRepo.cronSelect();
		userAnnouncementRepo.cronValDelete(id);
		System.out.print("welcome" + id);
		userAnnouncementRepo.cronUpdate();
		

	}
	


}
