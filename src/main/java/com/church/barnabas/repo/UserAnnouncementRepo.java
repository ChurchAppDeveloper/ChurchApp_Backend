package com.church.barnabas.repo;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.church.barnabas.entity.UserAnnouncement;

public interface UserAnnouncementRepo extends JpaRepository<UserAnnouncement, Integer> {
	

	UserAnnouncement findByTitle(String title);

	@Query(value = "select count(status) from user_announcement where status=true", nativeQuery = true)
	public int getAnnouncementCount();

	@Modifying
	@Transactional
	@Query(value = "update user_announcement set status=1 where id=?1 ", nativeQuery = true)
	public void updateAnnouncementCount(Integer id);

	@Modifying
	@Transactional
	@Query(value = "update  user_announcement set status=1 where id=?1", nativeQuery = true)
	public int updatedNewCount(Integer id);

	@Query(value = "select count(id) from user_announcement", nativeQuery = true)
	public int totalAnnouncementCount();
	
	@Query(value = "select filename from user_announcement where id=?1",nativeQuery = true)
	public String announcementDownload(Integer id);
	
	
	@Query(value="select id from user_announcement WHERE created_date < NOW() - INTERVAL 30 DAY",nativeQuery = true)
	public List<String> cronSelect();
	
	@Modifying
	@Transactional
	@Query(value="delete from user_announcement_notification_count where announcement_id in (?1)",nativeQuery = true)
	public void cronValDelete(List<String> id);
	
	
	
	@Modifying
	@Transactional
	@Query(value="delete from user_announcement WHERE created_date < NOW() - INTERVAL 30 DAY",nativeQuery = true)
	public void cronUpdate();
	
	
}
   