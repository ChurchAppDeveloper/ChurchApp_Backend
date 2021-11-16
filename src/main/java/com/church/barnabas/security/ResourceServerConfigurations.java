package com.church.barnabas.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@EnableResourceServer
@Configuration
public class ResourceServerConfigurations extends ResourceServerConfigurerAdapter {

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.anonymous().and().authorizeRequests().antMatchers(SecurityConfiguration.AUTH_WHITELIST).permitAll()
				.antMatchers(HttpMethod.POST, "/sendNotification**").permitAll()
				.antMatchers(HttpMethod.GET, "/downloadBannerImage**").permitAll()
				.antMatchers(HttpMethod.GET, "/download**").permitAll()
				.antMatchers(HttpMethod.GET, "/bannerImageListMobile**").permitAll()
				.antMatchers(HttpMethod.GET, "/getAnnouncementListMobile**").permitAll()
				.antMatchers(HttpMethod.GET, "/getAnnouncementCountMobile**").permitAll()
				.antMatchers(HttpMethod.GET, "/classifiedListMobile**").permitAll()
				.antMatchers(HttpMethod.GET, "/UnReadNotificationCount**").permitAll()
				.antMatchers(HttpMethod.GET, "/UpdatedAnnouncementCountMobile**").permitAll()
				.antMatchers(HttpMethod.POST, "/ReadNotificationsbyDeviceId**").permitAll()
				.antMatchers(HttpMethod.GET, "/downloadAnnouncement**").permitAll()
				.antMatchers(HttpMethod.GET, "/newTestAnnouncement**").permitAll()
				.antMatchers(HttpMethod.GET, "/getannouncementImage**").permitAll()
				.antMatchers(HttpMethod.GET,"/specificClassified**").permitAll()

				.antMatchers(HttpMethod.GET, "/classifiedListMobile**").permitAll()
				.antMatchers(HttpMethod.GET, "/massTimingListMobile**").permitAll()
				.antMatchers(HttpMethod.GET, "/userListMobile**").permitAll()
				.antMatchers(HttpMethod.GET, "/newdownloadAnnouncement**").permitAll()

				.antMatchers(HttpMethod.GET, "/createUserMobile**").permitAll()
				.antMatchers(HttpMethod.GET, "/updateUserMobile**").permitAll()
				.antMatchers(HttpMethod.GET, "/myprofileMobile**").permitAll()

				.anyRequest().authenticated();
	}

}