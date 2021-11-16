package com.church.barnabas.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.church.barnabas.entity.ChurchProfile;

public interface ChurchProfileRepo extends JpaRepository<ChurchProfile, Integer> {
	
	ChurchProfile findTopByOrderByIdDesc();

}
