package com.church.barnabas.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.church.barnabas.entity.Confession;

public interface ConfessionRepo extends JpaRepository<Confession,Integer>  {

}
