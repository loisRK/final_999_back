package com.spring.gugu.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.spring.gugu.entity.Diary;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(exclude = "fileDTOs")
public class DiaryDTO {
	private Long no;
	private String title;
	private String content;
	private LocalDateTime writtenDate;
	private LocalDateTime modifiedDate;
	private List<FileDTO> fileDTOs;
	
	public DiaryDTO(String title, String content) {
		this.title = title;
		this.content = content;
	}
	
	public void updateDiaryDTO(String title, String content) {
		this.title = title;
		this.content = content;
	}

	public static Diary dtoToEntity(DiaryDTO diaryDTO) {
		Diary diary = Diary.builder()
						.title(diaryDTO.getTitle())
						.content(diaryDTO.getContent())
						.build();
		return diary;
	}
	
}
