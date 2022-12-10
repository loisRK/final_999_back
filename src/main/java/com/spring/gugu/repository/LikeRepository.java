package com.spring.gugu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.gugu.entity.LikeTable;

@Repository
public interface LikeRepository extends JpaRepository<LikeTable, Long>{
	

}
