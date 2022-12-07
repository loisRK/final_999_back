package com.spring.gugu.service;

import java.util.List;
import java.util.NoSuchElementException;

import com.spring.gugu.common.dto.PageRequestDTO;
import com.spring.gugu.common.dto.PageResultDTO;
import com.spring.gugu.dto.DiaryDTO;
import com.spring.gugu.entity.Diary;

public interface DiaryService {
	
	public Long insertDiary(String title, String content);
	
	public DiaryDTO getDiaryByNo(Long diaryNo) throws NoSuchElementException;

	void insertBatchData(List<DiaryDTO> diaryList);
	
	public PageResultDTO<DiaryDTO, Diary> getList(PageRequestDTO requestDTO);

	void DiaryDTOUpdate(Long diaryNo, String title, String content);

	void deleteDiary(Long diaryNo);

	Long insertDiary(DiaryDTO diaryDTO);

//	public List<DiaryDTO> getAllDiary();
}
