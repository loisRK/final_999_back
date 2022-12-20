package com.spring.gugu.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.spring.gugu.dto.LikeTableDTO;

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
@Table(name = "like_table")
public class LikeTable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "like_no")
	private Long likeNo;

//	@Column(name = "user_id")
//	private Long userId;
	
//	@Column(name = "post_no")
//	private Long postNo;
	
	@ManyToOne(fetch = FetchType.LAZY )
	@JoinColumn(name = "user_id")
	@JsonIgnore
	private User user;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "post_no")
	@JsonIgnore
	private Post post;
	
	@Column(name = "after_like")
	private int afterLike;
	
	public static LikeTableDTO entityToDTO(LikeTable likeTable) {
		LikeTableDTO likeTableDTO = LikeTableDTO.builder()
								.likeNo(likeTable.getLikeNo())
//								.userId(likeTable.getUserId())
//								.postNo(likeTable.getPostNo())
								.userDTO(User.entityToDTO(likeTable.getUser()))
//								.postDTO(Post.entityToDTO(likeTable.getPost()))
								.afterlike(likeTable.getAfterLike())
								.build();
		return likeTableDTO;
								
	}
	
	public void updateLike(int changeLike) {
		this.afterLike = changeLike;
	}

}
