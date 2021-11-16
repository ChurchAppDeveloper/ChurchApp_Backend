package com.church.barnabas.security;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.church.barnabas.entity.UserTable;
import com.church.barnabas.repo.UserRepo;



@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepo userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.err.println("Login "+username);
        Date startdate=new Date();
        Calendar cal = Calendar.getInstance();
		  cal.setTime(startdate);
		  cal.add(Calendar.MINUTE, 2);
//      	System.out.println("cal"+cal.getInstance());
      	System.out.println("username "+username);
      	System.out.println("new Timestamp(startdate.getTime()) "+new Timestamp(startdate.getTime()));
      	System.out.println("new Timestamp(cal.getTimeInMillis()) "+new Timestamp(cal.getTimeInMillis()));


      	UserTable user=userRepository.findByContactNo(username);
      	if(user==null){
      		System.out.println("No User found!!!!!");
            throw new UsernameNotFoundException("could not find the user " + username + "/accessDenied");
      	}
      	
//      	if (user.getRoleName().equals("Admin")){    	
//        UserTable user1 = userRepository.findByContactNoAndOtpExpiryBetween2minutes(username,new Timestamp(startdate.getTime()),new Timestamp(cal.getTimeInMillis()));
      
      		 UserTable user1 = userRepository.findByContactNoAndOtpAndActiveStatus(username,user.getOtp(),true);
      	      
        if (user1 == null) {
        	System.out.println("No User found!!!!!");
            throw new UsernameNotFoundException("could not find the user " + username + "/accessDenied");
        }
        else {
          String roleName = "Admin";
            Set<GrantedAuthority> grantedAuthorities = new HashSet<>();        
           grantedAuthorities.add(new SimpleGrantedAuthority(roleName));            
            System.err.println(username+"123");
            return new CustomUserDetail(user1, roleName);
        	}
//      	} else {    	
//            String roleName = user.getRoleName();
//            Set<GrantedAuthority> grantedAuthorities = new HashSet<>();        
//            grantedAuthorities.add(new SimpleGrantedAuthority(roleName));            
//            System.err.println(username+"123");
//            return new CustomUserDetail(user, roleName);   		
//      	}
}
}