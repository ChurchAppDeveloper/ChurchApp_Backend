package com.church.barnabas.repo;



import org.springframework.data.jpa.repository.JpaRepository;

import com.church.barnabas.entity.MassTiming;

public interface MassTimingRepo extends JpaRepository<MassTiming, Integer> {
	
	
//	List<MassTiming> findByDay(String day);
	
//	List<MassTiming> findByDateAfter();

}
	