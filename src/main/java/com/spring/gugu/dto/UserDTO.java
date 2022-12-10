package com.spring.gugu.dto;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.spring.gugu.entity.User;

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
@ToString
public class UserDTO {
	private Long kakaoId;
	private String kakaoNickname;
	private String kakaoProfileImg;
	private String kakaoEmail;
	private String ageRange;
	private String gender;
	private int postCnt;
	private Timestamp createTime;
		
	
	public static User dtoToEntity(UserDTO userDTO) {
		User user = User.builder()
						.kakaoId(userDTO.getKakaoId())
						.kakaoNickname(userDTO.getKakaoNickname())
						.kakaoProfileImg(userDTO.getKakaoProfileImg())
						.kakaoEmail(userDTO.getKakaoEmail())
						.ageRange(userDTO.getAgeRange())
						.gender(userDTO.getGender())
						.postCnt(userDTO.getPostCnt())
						.build();
		return user;
	}
	
}
