package com.spring.gugu.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.spring.gugu.dto.FileDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Builder
public class FileEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "file_no", nullable = false)	// nullable : not null(false) 설정
	private Long no;
	
	// 특정 데이터를 가져올 때에만 실행되도록 하는 설정 (fetchtype -> LAZY)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "diary_no")
	private Diary diary;
	
	private String originalFileName;
	
	private String fileName;
	
	private String filePath;
	
	public void updateDiary(Diary diary) {
		this.diary = diary;
	}
	
	//  entity -> DTO
	public static FileDTO entityToDto(FileEntity file) {
		FileDTO fileDTO = FileDTO.builder()
						.fileNo(file.getNo())
                        .originalFileName(file.getOriginalFileName())
                        .fileName(file.getFileName())
                        .filePath(file.getFilePath())
//                        .diaryDTO(Diary.entityToDTO(file.getDiary()))
                        .build();
		return fileDTO;
	}

	
	
}
