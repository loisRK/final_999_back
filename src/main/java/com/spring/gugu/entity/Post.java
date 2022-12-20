package com.spring.gugu.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.data.annotation.LastModifiedDate;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.spring.gugu.dto.PostDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"likes"})
@Getter
@Builder
@Table(name = "post")
public class Post {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "post_no")
	private Long postNo;
	
	// user table - user_id FK
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
//	@NotFound(action = NotFoundAction.IGNORE) // 값이 발견되지 않으면 무시
	@JsonBackReference
	private User user;
	
	/*
	 * updatable = false : column에 대한 업데이트 방지
	 */
	@Column(name = "post_date", updatable = false)
	@CreationTimestamp
	private LocalDateTime postDate;

	@LastModifiedDate
	@Column(name = "modified_date")
	private LocalDateTime modifiedDate;
	
	@Column(name = "post_lat")
	private Double postLat;
	
	@Column(name = "post_long")
	private Double postLong;
	
	@Column(name = "post_content")
	private String postContent;
	
	@Column(name = "like_cnt")
	private Long likeCnt;
	
	@Column(name = "post_img")
	private String postImg;
	
	@OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
	@JsonIgnore
	private List<LikeTable> likes = new ArrayList<LikeTable>();
	
	public static PostDTO entityToDTO(Post post) {
		PostDTO postDTO = PostDTO.builder()
								.postNo(post.getPostNo())
								.userDTO(User.entityToDTO(post.getUser()))
								.postContent(post.getPostContent())
								.postDate(post.getPostDate())
								.modifiedDate(post.getModifiedDate())
								.postLat(post.getPostLat())
								.postLong(post.getPostLong())
								.likeCnt(post.getLikeCnt())
								.postImg(post.getPostImg())
								.likes(post.getLikes().stream().map(like -> LikeTable.entityToDTO(like)).collect(Collectors.toList()))
								.build();
		return postDTO;
	}
	
	public void updatePost(String postContent) {
		this.postContent = postContent;
	}
	
	public void updatePost(String postContent, String postImg) {
		this.postContent = postContent;
		this.postImg = postImg;
	}
	
	// 좋아요 추가 메소드
	public void addLike() {
		this.likeCnt = this.likeCnt + 1;
	}

	public void minusLike() {
		this.likeCnt = this.likeCnt - 1;
	}
}
