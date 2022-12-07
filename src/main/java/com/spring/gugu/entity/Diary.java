package com.spring.gugu.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.spring.gugu.dto.DiaryDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude="files")
@Getter
//entity 수정사항을 계속 확인하게 하는 annotation, @createdDate, @LastModifiedDate annoatation사용을 위한 설정
@EntityListeners(AuditingEntityListener.class)	
@Builder
public class Diary {
	@Id
	@Column(name = "diary_no")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long no;
	
	private String title;
	
	private String content;
	
	@CreatedDate	// 처음 생성 시 저장하는 값
	@Column(updatable = false)	// column에 대한 업데이트 방지 설정
	private LocalDateTime writtenDate;
	
	@LastModifiedDate
	private LocalDateTime modifiedDate;
	
	// 양방향참조
	// 기준점 : 외래키
	@OneToMany(mappedBy = "diary", cascade = CascadeType.ALL)
	private List<FileEntity> files = new ArrayList<FileEntity>();
	
	public static DiaryDTO entityToDTO(Diary diary) {
		DiaryDTO diaryDTO = DiaryDTO.builder()
									.no(diary.getNo())
									.title(diary.getTitle())
									.content(diary.getContent())
									.writtenDate(diary.getWrittenDate())
									.modifiedDate(diary.getModifiedDate())
									.fileDTOs(diary.getFiles().stream()
																.map(file -> file.entityToDto(file))
																.collect(Collectors.toList()))
									.build();
		return diaryDTO;
	}
	
	public void updateDiary(String title, String content) {
		this.title = title;
		this.content = content;
	}

}
