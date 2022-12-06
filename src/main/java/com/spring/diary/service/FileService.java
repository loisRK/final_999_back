package com.spring.diary.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.spring.diary.dto.FileDTO;
import com.spring.diary.entity.Diary;
import com.spring.diary.entity.FileEntity;

public interface FileService {

	public void insertFile(List<MultipartFile> files, Long diaryNo);

	public List<FileDTO> getByDiaryNo(Long diaryNo);

	void deleteFile(Long fileNo);
	
}
