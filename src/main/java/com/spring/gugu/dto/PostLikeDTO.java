package com.spring.gugu.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.spring.gugu.entity.LikeTable;
import com.spring.gugu.entity.Post;

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
public class PostLikeDTO {
	private Long postNo;
	private LocalDateTime postDate;
	private String postContent;
	private Long likeCnt;
	private String postImg;
	private Long kakaoId;
	private String kakaoNickname;
	private String kakaoProfileImg;
	private int afterLike;
	
	public static PostLikeDTO fromEntities(Post post, int afterLike) { 
		PostLikeDTO postLikeDTO = PostLikeDTO.builder()
											 .postNo(post.getPostNo())
											 .postDate(post.getPostDate())
											 .postContent(post.getPostContent())
											 .likeCnt(post.getLikeCnt())
											 .postImg(post.getPostImg())
											 .kakaoId(post.getUser().getKakaoId())
											 .kakaoNickname(post.getUser().getKakaoNickname())
											 .kakaoProfileImg(post.getUser().getKakaoProfileImg())
											 .afterLike(afterLike)
											 .build();
		return postLikeDTO;
	}
	
}
