package com.spring.gugu.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

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
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "post_no")
	private Post post;
	
	@Column(name = "after_like")
	private int afterLike;
	
	public static LikeTableDTO entityToDTO(LikeTable likeTable) {
		LikeTableDTO likeTableDTO = LikeTableDTO.builder()
								.likeNo(likeTable.getLikeNo())
								.userDTO(User.entityToDTO(likeTable.getUser()))
								.postDTO(Post.entityToDTO(likeTable.getPost()))
								.afterlike(likeTable.getAfterLike())
								.build();
		return likeTableDTO;
								
	}

}
