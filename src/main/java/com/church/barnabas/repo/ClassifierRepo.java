package com.church.barnabas.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.church.barnabas.entity.BusinessTypes;
import com.church.barnabas.entity.Classified;

public interface ClassifierRepo extends JpaRepository<Classified, Integer>{
	
	
//or business_name like :startingLetter%
	//where  name  like 'g%' or phone_number  like '5%' or business_name like 'f%' ;


   
   @Query(value="select business_name from business_types where business_id=?1",nativeQuery  =true)
   String getBusinessName(Integer id); 
   
   @Query(value="select * from classified where business_name  like :startingLetter%  or phone_number  like :startingLetter%  or name like :startingLetter%", nativeQuery  =true)
	List<Classified> getValuesOnLetters(@Param("startingLetter") String startingLetter);
}