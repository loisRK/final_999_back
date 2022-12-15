package com.spring.gugu.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.spring.gugu.entity.Taboo;

@Repository
public interface TabooRepository extends JpaRepository<Taboo, Long> {

	// 룸 넘버로 금기어 목록 찾기 함수가 자동 생성되도록 입력
	List<Taboo> findAllByRoomNo(long roomNo);
	
	// 금기어로 조회하고 해당하는 금기어 삭제하는 함수 생성
	@Transactional
    @Modifying
    @Query("delete from Taboo t where t.tabooWord = :ids")
    void deleteByTabooWord(@Param("ids") String ids);
}
