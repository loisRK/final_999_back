package com.spring.diary.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
@Table(name = "kakao")
public class KakaoEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)		// auto-increment 일 때 꼭 붙여줘야함
	@Column(name = "kakao_code")
	private Long kakaoCode;
	
	@Column(name = "kakao_id")
	private Long kakaoId;
	
	@Column(name = "kakao_profile_img")
	private String kakaoProfileImg;
	
	@Column(name = "kakao_nickname")
	private String kakaoNickname;
	
	@Column(name = "kakao_email")
	private String kakaoEmail;
	
	@Column(name = "kakao_role")
	private String kakaoRole;
	
	@Column(name = "create_time")
	@CreationTimestamp
	private Timestamp createTime;

	@Builder
	public KakaoEntity(Long kakaoId, String kakaoProfileImg, String kakaoNickname, 
			String kakaoEmail, String kakaoRole) {
		this.kakaoId = kakaoId;
		this.kakaoProfileImg = kakaoProfileImg;
		this.kakaoNickname = kakaoNickname;
		this.kakaoEmail = kakaoEmail;
		this.kakaoRole = kakaoRole;
	}
}
