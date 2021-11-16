package com.church.barnabas.service;


import java.util.Date;

import com.church.barnabas.dto.MassTimingDto;
import com.church.barnabas.dto.ResultReturnObject;

public interface MassTimingService  {

	ResultReturnObject createMassTiming(MassTimingDto massTimingDto,String contactNo) ;
	
	ResultReturnObject updateMassTiming(MassTimingDto massTimingDto,String contactNo,Integer id);
	
	ResultReturnObject massTimingList(String contactNo);

	ResultReturnObject massTimingListMobile();

	ResultReturnObject deleteMassTiming(Integer timingId,String contactNo);


	 Date stringToDate(String stringDate) ;
	
	 String dateToString(Date date);
	 
	 ResultReturnObject sendMassTimingNotification();

}
