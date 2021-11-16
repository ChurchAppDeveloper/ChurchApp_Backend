package com.church.barnabas.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.church.barnabas.entity.UserAnnouncementNotifyCount;

public interface NotificationRepo extends JpaRepository<UserAnnouncementNotifyCount,Integer >{
    

 

 
 @Query(value= "select count(*) from user_announcement_notification_count where device_id=?1", nativeQuery = true)
 public int readAnnouncementCount(String id);


 
	

}
