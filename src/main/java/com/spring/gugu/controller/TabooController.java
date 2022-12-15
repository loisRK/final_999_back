package com.spring.gugu.controller;

import java.util.List;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spring.gugu.dto.TabooDTO;
import com.spring.gugu.service.TabooServiceImpl;

import lombok.RequiredArgsConstructor;

@RestController		// 페이지 전환이 필요없으므로 restController 사용
@RequestMapping(value = "/api")
@CrossOrigin(origins = {"http://localhost:3000"})
@DynamicUpdate
@RequiredArgsConstructor
public class TabooController {
	
	@Autowired
	final TabooServiceImpl tabooService;
	
	// 금기어 추가 (insert)
	@PostMapping("/taboo")
	public void insertTaboo(@RequestParam("roomNo") long roomNo, @RequestParam("tabooWord") String tabooWord ) {
		tabooService.insertTaboo(roomNo, tabooWord);
	}
	
	// 모든 금기어 출력하기 (alldata)
	@GetMapping("/tabooList/{roomNo}")
	public List<String> getAllTabooList(@PathVariable long roomNo) {
		
		List<String> taboolist = tabooService.getAllTabooList(roomNo);
		
		return taboolist;
		
	}
	
	
	// 해당 금기어 delete
	@GetMapping("/deleteTaboo/{tabooWord}")
	public void deleteTaboo(@PathVariable String tabooWord) {
		
		tabooService.deleteTaboo(tabooWord);
	}
}
