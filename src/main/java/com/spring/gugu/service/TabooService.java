package com.spring.gugu.service;

import java.util.List;

import com.spring.gugu.dto.TabooDTO;

public interface TabooService {
	
	public void insertTaboo(long roomNo,String tabooWord);

	public List<String> getAllTabooList(long roomNo);
	
	public void deleteTaboo(String tabooWord);
}
