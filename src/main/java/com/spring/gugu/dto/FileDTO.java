package com.spring.gugu.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.spring.gugu.entity.Diary;
import com.spring.gugu.entity.FileEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class FileDTO {
	private Long fileNo;
	private String originalFileName;
	private String fileName;
	private String filePath;
//	private DiaryDTO diaryDTO;
	
	public static FileEntity dtoToEntity(FileDTO fileDTO) {
		FileEntity fileEntity = FileEntity.builder()
										.originalFileName(fileDTO.getOriginalFileName())
										.fileName(fileDTO.getFileName())
										.filePath(fileDTO.getFilePath())
										.build();
		return fileEntity;
	}
}
