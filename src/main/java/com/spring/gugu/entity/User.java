package com.spring.gugu.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
@Table(name = "user")
public class User {
	@Id
	@Column(name = "user_id")
	private Long kakaoId;
	
	@Column(name = "user_nickname")
	private String kakaoNickname;
	
	@Column(name = "profile_img")
	private String kakaoProfileImg;
	
	@Column(name = "user_email")
	private String kakaoEmail;
	
	@Column(name = "age_range")
	private String ageRange;
	
	@Column(name = "gender")
	private String gender;
	
	@Column(name = "post_cnt")
	private int postCnt;
	
	@Column(name = "create_time")
	@CreationTimestamp
	private Timestamp createTime;

	@Builder
	public User(Long kakaoId, String kakaoNickname, String kakaoProfileImg,  
			String kakaoEmail, String ageRange, String gender) {
		this.kakaoId = kakaoId;
		this.kakaoNickname = kakaoNickname;
		this.kakaoProfileImg = kakaoProfileImg;
		this.kakaoEmail = kakaoEmail;
		this.ageRange = ageRange;
		this.gender = gender;
	}
}