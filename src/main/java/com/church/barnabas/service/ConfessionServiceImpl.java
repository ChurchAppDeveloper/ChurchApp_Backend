package com.church.barnabas.service;

import java.sql.Time;
import java.text.ParseException;
import java.time.LocalTime;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.church.barnabas.dto.ConfessionDto;
import com.church.barnabas.dto.ResultReturnObject;
import com.church.barnabas.entity.Confession;
import com.church.barnabas.entity.ScheduleConfession;
import com.church.barnabas.entity.UserTable;
import com.church.barnabas.repo.ConfessionRepo;
import com.church.barnabas.repo.ScheduleConfessionRepo;
import com.church.barnabas.repo.UserRepo;


@Service
public class ConfessionServiceImpl implements ConfessionService{
	
	@Autowired
	private ConfessionRepo confessionRepo;

	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private ScheduleConfessionRepo scheduleConfessionRepo;
	
	@Override
	public ResultReturnObject createConfession(String contactNo, ConfessionDto confessionDto) {

		confessionRepo.deleteAll();
		Confession confession=new Confession();
		
		confession.setConfessionDate(confessionDto.getConfessionDate());
		confession.setConfessionStartTime(confessionDto.getConfessionStartTime());
		confession.setConfessionEndTime(confessionDto.getConfessionEndTime());
		confession.setConfessionDuration(confessionDto.getConfessionDuration());
		UserTable user= userRepo.findByContactNo(contactNo);
		confession.setCreatedBy(user.getId());
		confession.setModifiedBy(user.getId());
		confession.setCreatedTime(new Date());
		confession.setModifiedTime(new Date());

		confessionRepo.save(confession);
		
		return new ResultReturnObject(true,"Successfully Confession is created!!","");
	}

	@Override
	public ResultReturnObject confessionList(String contactNo) {
		
		return new ResultReturnObject(true,"Successfully Confession is created!!",confessionRepo.findAll());
	}

	@Override
	public ResultReturnObject scheduleConfession(String contactNo) {

		Confession confession=confessionRepo.findAll().get(0);
		
		
//		for(Confession confession:confessionList.) {
			
			
			Time lastSchedule=scheduleConfessionRepo.findByLastSchedule( confession.getConfessionEndTime());
			
			if (lastSchedule!=null){
				return new ResultReturnObject(false,"No slots available !!","");
			}else {
			
			Time currentScheduleEndTime=scheduleConfessionRepo.findByCurrentSchedule(confession.getConfessionStartTime(), confession.getConfessionEndTime());
			if (currentScheduleEndTime!=null){
				
			LocalTime	nextScheduleEndTime=currentScheduleEndTime.toLocalTime().plusMinutes(confession.getConfessionDuration());
			
			ScheduleConfession scheduleConfession=new ScheduleConfession();
			scheduleConfession.setStartTime(currentScheduleEndTime);
			scheduleConfession.setEndTime(java.sql.Time.valueOf(nextScheduleEndTime));
			scheduleConfession.setScheduleDuration(confession.getConfessionDuration());
			scheduleConfession.setContributorId(userRepo.findByContactNo(contactNo));
			scheduleConfessionRepo.save(scheduleConfession);
			
			}else {
				LocalTime	nextScheduleEndTime=confession.getConfessionStartTime().toLocalTime().plusMinutes(confession.getConfessionDuration());

				ScheduleConfession scheduleConfession=new ScheduleConfession();
				scheduleConfession.setStartTime(confession.getConfessionStartTime());
				scheduleConfession.setEndTime(java.sql.Time.valueOf(nextScheduleEndTime));
				scheduleConfession.setScheduleDuration(confession.getConfessionDuration());
				scheduleConfession.setContributorId(userRepo.findByContactNo(contactNo));
				scheduleConfessionRepo.save(scheduleConfession);
				
			}
			return new ResultReturnObject(true,"Confession Scheduled Successfully  !!","");

		}

		
	}

	@Override
	public ResultReturnObject availableConfessionSlots(String contactNo) throws ParseException {
		
		Confession confession=confessionRepo.findAll().get(0);

		Time lastSchedule=scheduleConfessionRepo.findByLastSchedule( confession.getConfessionEndTime());
		
		if (lastSchedule!=null){
			return new ResultReturnObject(false,"No slots available !!","");
		}else {
//			Integer sum=scheduleConfessionRepo.sumOfSCheduleTime(confession.getConfessionStartTime(), confession.getConfessionEndTime());
//			
//			SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
//			Date date1 = format.parse( confession.getConfessionStartTime().toString());
//			Date date2 = format.parse(confession.getConfessionEndTime().toString());
//			long difference = date2.getTime() - date1.getTime(); 
//			if (sum!=null && sum!=0 ){
//			    long minutes = TimeUnit.MILLISECONDS.toMinutes(difference);
//			    TimeUnit.MINUTES.toMillis(sum);
//
//			 long  remainingMinutes=minutes-sum;
//			 
//			 long remainingSlots=remainingMinutes/confession.getConfessionDuration();
//
//			
//				System.out.println("minutes.."+minutes);
//				System.out.println("remainingMinutes.."+remainingMinutes);
//
//				System.out.println("remainingSlots.."+remainingSlots);

//			}
			
			Time   currentScedule=scheduleConfessionRepo.findByCurrentSchedule(confession.getConfessionStartTime(),confession.getConfessionEndTime());
			
			

		}
		
		
		return null;
	}

	@Override
	public ResultReturnObject confessionSlotForContributor(String contactNo) {
		
		return null;
	}
	
	
	

}
