package com.spring.gugu.entity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.spring.gugu.dto.UserDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString(exclude = {"posts", "likes"})
//entity 수정사항을 계속 확인하게 하는 annotation, @createdDate, @LastModifiedDate annoatation사용을 위한 설정
@EntityListeners(AuditingEntityListener.class)	
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
	
	private String gender;
	
	@Column(name = "post_cnt")
	private int postCnt;
	
	@Column(name = "create_time")
	@CreationTimestamp
	private Timestamp createTime;
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
	@JsonIgnore
	private List<Post> posts = new ArrayList<Post>();
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
	private List<LikeTable> likes = new ArrayList<LikeTable>();
	
//	@OneToOne(mappedBy = "user")
//	private Room room;
	
	@Builder
	public User(Long kakaoId, String kakaoNickname, String kakaoProfileImg,  
			String kakaoEmail, String ageRange, String gender, int postCnt) {
		this.kakaoId = kakaoId;
		this.kakaoNickname = kakaoNickname;
		this.kakaoProfileImg = kakaoProfileImg;
		this.kakaoEmail = kakaoEmail;
		this.ageRange = ageRange;
		this.gender = gender;
		this.postCnt = postCnt;
	}
	
	public static UserDTO entityToDTO(User user) {
		UserDTO userDTO = UserDTO.builder()
								.kakaoId(user.getKakaoId())
								.kakaoNickname(user.getKakaoNickname())
								.kakaoProfileImg(user.getKakaoProfileImg())
								.kakaoEmail(user.getKakaoEmail())
								.ageRange(user.getAgeRange())
								.gender(user.getGender())
								.postCnt(user.getPostCnt())
								.build();
		return userDTO;
		
	}
	public void updateUser(String kakaoEmail, String kakaoNickname, String kakaoProfileImg) {
		this.kakaoEmail = kakaoEmail;
		this.kakaoNickname = kakaoNickname;
		this.kakaoProfileImg = kakaoProfileImg;
	}
	public void updateUser(String kakaoEmail, String kakaoNickname) {
		this.kakaoEmail = kakaoEmail;
		this.kakaoNickname = kakaoNickname;
	}
	
	// 포스팅 개수 +1 추가
	public void addPostCnt() {
		this.postCnt = this.postCnt + 1;
	}
	
	// 포스팅 개수 -1 제거
	public void minusPostCnt() {
		this.postCnt = this.postCnt - 1;
	}

}
