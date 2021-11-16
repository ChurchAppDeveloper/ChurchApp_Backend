package com.church.barnabas.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.church.barnabas.entity.BusinessTypes;

public interface BusinessTypeRepo extends JpaRepository<BusinessTypes, Integer> {
	

	@Query(value="select * from business_types where business_name like :search% ",nativeQuery  =true)
	List<BusinessTypes> searchByBusinessTypeName(@Param("search") String search);
	
	
	BusinessTypes findByBusinessName(String businessName);
//	
//	@Query(value="select * from business_types where business_name LIKE :startingLetter%", nativeQuery  =true)
//	List<BusinessTypes> getValuesOnLetters(String startingLetter);
//	
}
