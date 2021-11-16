package com.church.barnabas.security;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
	    return super.authenticationManagerBean();
	}
	@SuppressWarnings("deprecation")
	@Bean
	public static NoOpPasswordEncoder passwordEncoder() {
	return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
	}
	 static final String[] AUTH_WHITELIST = {
	        "/swagger-resources/**",
	        "/swagger-ui.html",
	        "/v2/api-docs",
	        "/webjars/**",
	        "/", "/oauth/token","/sendNotification**","/swagger-ui/**","/v2/**","/getannouncementImage**"
	};
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.anonymous().and()
                .authorizeRequests()
                .antMatchers(AUTH_WHITELIST).permitAll()
                .antMatchers(HttpMethod.POST,"/sendNotification**").permitAll()
                .antMatchers(HttpMethod.GET,"/myprofile**").permitAll()
                .antMatchers(HttpMethod.GET,"/getAnnouncementList**").permitAll()
                .antMatchers(HttpMethod.GET,"/getAnnouncementCount**").permitAll()
                .antMatchers(HttpMethod.GET,"/bannerImageListMobile**").permitAll()
                .antMatchers(HttpMethod.GET,"/getAnnouncementListMobile**").permitAll()
                .antMatchers(HttpMethod.GET,"/getAnnouncementCountMobile**").permitAll()
                .antMatchers(HttpMethod.GET,"/classifiedListMobile**").permitAll()
                .antMatchers(HttpMethod.GET,"/specificClassified**").permitAll()
                .antMatchers(HttpMethod.GET,"/massTimingListMobile**").permitAll()
                .antMatchers(HttpMethod.GET,"/userListMobile**").permitAll()
                .antMatchers(HttpMethod.GET,"/createUserMobile**").permitAll()
                .antMatchers(HttpMethod.GET,"/updateUserMobile**").permitAll()
                .antMatchers(HttpMethod.GET,"/myprofileMobile**").permitAll()

                

                .anyRequest()
                .authenticated();
        http.cors();
    }

}
