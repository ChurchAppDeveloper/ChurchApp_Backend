package com.church.barnabas.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.church.barnabas.dto.MassTimingDto;
import com.church.barnabas.dto.ResultReturnObject;
import com.church.barnabas.entity.MassTiming;
import com.church.barnabas.repo.MassTimingRepo;

@Service
public class MassTimingServiceImpl implements MassTimingService {

	@Autowired
	private MassTimingRepo massTimingRepo;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public ResultReturnObject createMassTiming(MassTimingDto massTimingDto, String contactNo) {

		MassTiming massTiming = new MassTiming();

		massTiming.setStartTime(stringToDate(massTimingDto.getStartTime()));
		massTiming.setEndTime(stringToDate(massTimingDto.getEndTime()));
		massTiming.setStartDate(stringToDate(massTimingDto.getStartDate()));
		massTiming.setEndDate(stringToDate(massTimingDto.getEndDate()));
		massTiming.setScheduleType(massTimingDto.getScheduleType());
		massTiming.setPrimaryColour(massTimingDto.getPrimaryColour());
		massTiming.setSecondaryColour(massTimingDto.getSecondaryColour());
		massTimingRepo.save(massTiming);
		return new ResultReturnObject(true, "Successfully MassTiming is created!!", "");

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public ResultReturnObject updateMassTiming(MassTimingDto massTimingDto, String contactNo,Integer id) {

		massTimingRepo.deleteById(id);
		MassTiming massTiming = new MassTiming();

		massTiming.setStartTime(stringToDate(massTimingDto.getStartTime()));
		massTiming.setEndTime(stringToDate(massTimingDto.getEndTime()));
		massTiming.setStartDate(stringToDate(massTimingDto.getStartDate()));
		massTiming.setEndDate(stringToDate(massTimingDto.getEndDate()));
		massTiming.setScheduleType(massTimingDto.getScheduleType());
		massTiming.setPrimaryColour(massTimingDto.getPrimaryColour());
		massTiming.setSecondaryColour(massTimingDto.getSecondaryColour());
		massTimingRepo.save(massTiming);

		return new ResultReturnObject(true, "Successfully MassTiming is Updated!!", "");
	}

	@Override
	public ResultReturnObject massTimingList(String contactNo) {

		List<MassTimingDto> massTimingDtos = new ArrayList<MassTimingDto>();
		List<MassTiming> massTiming = massTimingRepo.findAll();
		for (MassTiming list : massTiming) {
			MassTimingDto massTimingDto = new MassTimingDto();
			massTimingDto.setId(list.getMassTimingId());
			massTimingDto.setStartTime(dateToString(list.getStartTime()));
			massTimingDto.setEndTime(dateToString(list.getEndTime()));
			massTimingDto.setStartDate(dateToString(list.getStartDate()));
			massTimingDto.setEndDate(dateToString(list.getEndDate()));
			massTimingDto.setPrimaryColour(list.getPrimaryColour());
			massTimingDto.setSecondaryColour(list.getSecondaryColour());
			
            massTimingDto.setScheduleType(list.getScheduleType());
			massTimingDtos.add(massTimingDto);
		}
		return new ResultReturnObject(true, "MassTiming Listed Successfully!!", massTimingDtos);
	}

	@Override
	public ResultReturnObject massTimingListMobile() {

		List<MassTimingDto> massTimingDtos = new ArrayList<MassTimingDto>();
		List<MassTiming> massTiming = massTimingRepo.findAll();
		for (MassTiming list : massTiming) {
			MassTimingDto massTimingDto = new MassTimingDto();
			massTimingDto.setId(list.getMassTimingId());
			massTimingDto.setStartTime(dateToString(list.getStartTime()));
			massTimingDto.setEndTime(dateToString(list.getEndTime()));
			massTimingDto.setStartDate(dateToString(list.getStartDate()));
			massTimingDto.setEndDate(dateToString(list.getEndDate()));
			massTimingDto.setPrimaryColour(list.getPrimaryColour());
			massTimingDto.setSecondaryColour(list.getSecondaryColour());
			massTimingDto.setScheduleType(list.getScheduleType());
			massTimingDtos.add(massTimingDto);
		}
		return new ResultReturnObject(true, "MassTiming Listed Successfully!!", massTimingDtos);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public ResultReturnObject deleteMassTiming(Integer timingId, String contactNo) {

		massTimingRepo.delete(massTimingRepo.findById(timingId).get());
		return new ResultReturnObject(true, "Successfully MassTiming is Deleted!!", "");

	}

	@Override
	public Date stringToDate(String stringDate) {

		Date date1 = new Date();

		try {
			date1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(stringDate);
			System.out.println(stringDate + "\t" + date1);

		} catch (Exception e) {

		}
		return date1;

	}

	@Override
	public String dateToString(Date date) {

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String strDate = dateFormat.format(date);
		return strDate;

	}

	@Override
	public ResultReturnObject sendMassTimingNotification() {
		return null;

//		
//		Map<String, String> map=new HashMap<String, String>();
//		map.put("title", announcementDTO.getTitle());
//		map.put("description", announcementDTO.getDescription());
//		map.put("imageurl", "/getannouncementImage?announcementid="+userAnnounce.getId()+"&fileName="+file.getOriginalFilename());
//		pushNotificationService.sendNotification(new Note("Announcement",announcementDTO.getTitle(),map,""), "barnabas");	}

	}
}
