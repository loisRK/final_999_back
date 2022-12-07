package com.spring.gugu.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.spring.gugu.dto.FileDTO;
import com.spring.gugu.entity.Diary;
import com.spring.gugu.entity.FileEntity;

public interface FileService {

	public void insertFile(List<MultipartFile> files, Long diaryNo);

	public List<FileDTO> getByDiaryNo(Long diaryNo);

	void deleteFile(Long fileNo);
	
}
