package com.church.barnabas.service;

import java.text.ParseException;

import com.church.barnabas.dto.ConfessionDto;
import com.church.barnabas.dto.ResultReturnObject;

public interface ConfessionService {
	
	
ResultReturnObject createConfession(String contactNo,ConfessionDto confessionDto);

ResultReturnObject confessionList(String contactNo);

ResultReturnObject scheduleConfession(String contactNo );


ResultReturnObject availableConfessionSlots(String contactNo )throws ParseException;

ResultReturnObject confessionSlotForContributor(String contactNo );


}
