package com.church.barnabas.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.church.barnabas.entity.UserAnnouncementNotifyCount;

public interface UserAnnouncementNotifyRepo extends JpaRepository<UserAnnouncementNotifyCount,Integer > {
	@Query(value = "select * from user_announcement_notification_count where device_id=?1", nativeQuery = true)
	public List<UserAnnouncementNotifyCount> deviceIdDetails(String id);
	
	@Query(value="SELECT * FROM user_announcement_notification_count where device_id=?1 and announcement_id = ?2",nativeQuery = true)
	public List<UserAnnouncementNotifyCount>ReadCount(String id,Integer id1);
	

}
