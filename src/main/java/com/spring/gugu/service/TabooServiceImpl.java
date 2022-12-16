package com.spring.gugu.service;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import com.spring.gugu.dto.TabooDTO;
import com.spring.gugu.entity.Taboo;
import com.spring.gugu.repository.TabooRepository;

@Service
public class TabooServiceImpl {

	@Autowired
	TabooRepository tabooRepo;
	
	public void insertTaboo(long roomNo,String tabooWord) {
		// 금기어 DTO 객체 생성
		TabooDTO tabooDTO = new TabooDTO(roomNo, tabooWord);
		
		// 생성한 DTO로 -> ENTITY 변경 후 DB에 저장하는 과정
		Taboo taboo = tabooDTO.dtoToEntity(tabooDTO);
				
		// 테이블에 추가
		tabooRepo.save(taboo);
		
	}

	public List<String> getAllTabooList(long roomNo) {
		
		List<Taboo> allTaboo = tabooRepo.findAllByRoomNo(roomNo);
		
		// Taboo 타입을 DTO 타입으로 변환
		Function<Taboo, String> fn = (taboo -> Taboo.entityToDTO(taboo).getTabooWord());
		List<String> tabooList = allTaboo.stream()
										 .map(fn)
										 .collect(Collectors.toList());
		
		System.out.println(tabooList);
										 
		
		return tabooList;
	}
	
	@Transactional
	public void deleteTaboo(String tabooWord) {
		
		tabooRepo.deleteByTabooWord(tabooWord);
	}
	
	
}
