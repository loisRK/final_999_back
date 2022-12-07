package com.spring.gugu.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.spring.gugu.entity.Diary;

@Repository
public interface DiaryRepository extends JpaRepository<Diary, Long>, PagingAndSortingRepository<Diary, Long> {

	
}
