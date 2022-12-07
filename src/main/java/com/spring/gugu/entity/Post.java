package com.spring.gugu.entity;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.LastModifiedDate;

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
@Table(name = "post")
public class Post {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "post_no")
	private long postNo;
	
	// user table - user_id FK
	@Column(name = "user_id")
	private long userId;
	
	/*
	 * updatable = false : column에 대한 업데이트 방지
	 */
	@Column(name = "post_date", updatable = false)
	@CreationTimestamp
	private Timestamp postDate;

	@LastModifiedDate
	@Column(name = "modified_date")
	private LocalDateTime modifiedDate;
	
	@Column(name = "post_lat")
	private int postLat;
	
	@Column(name = "post_long")
	private int postLong;
	
	@Column(name = "post_content")
	private String psotContent;
	
	@Column(name = "like_cnt")
	private int likeCnt;
	
	@Column(name = "post_img")
	private String postImg;

}