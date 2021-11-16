package com.church.barnabas.repo;

import java.sql.Time;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.church.barnabas.entity.ScheduleConfession;

public interface ScheduleConfessionRepo extends JpaRepository<ScheduleConfession, Integer> {
	
	@Query(value="SELECT i.end_time FROM schedule_confession i where i.start_time >= ?1 and i.end_time < ?2 order by i.end_time desc LIMIT 1",nativeQuery = true)
	Time findByCurrentSchedule(Time startTime,Time endTime);
	
	@Query(value="SELECT i.end_time FROM schedule_confession i where  i.end_time = ?1 ",nativeQuery = true)
	Time findByLastSchedule(Time endTime);
	
	@Query(value="SELECT count(*) FROM schedule_confession i where i.start_time >= ?1 and i.end_time <= ?2 ",nativeQuery = true)
	Integer findNoOfSchedules(Time startTime,Time endTime);
	
	
	@Query(value="SELECT sum(i.schedule_duration) FROM schedule_confession i where i.start_time >= ?1 and i.end_time <= ?2 ",nativeQuery = true)
	Integer sumOfSCheduleTime(Time startTime,Time endTime);
}
