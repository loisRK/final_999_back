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
@ToString(exclude = "userDTO")
public class PostDTO {
	private Long postNo;
	private UserDTO userDTO;
	private LocalDateTime postDate;
	private LocalDateTime modifiedDate;
	private Double postLat;
	private Double postLong;
	private String postContent;
	private Long likeCnt;
	private String postImg;
	private List<LikeTableDTO> likes = new ArrayList<LikeTableDTO>();
	
	public static Post dtoToEntity(PostDTO postDTO) {
		Post post = Post.builder()
						.postNo(postDTO.getPostNo())
						.user(UserDTO.dtoToEntity(postDTO.getUserDTO()))
						.postDate(postDTO.getPostDate())
						.modifiedDate(postDTO.getModifiedDate())
						.postLat(postDTO.getPostLat())
						.postLong(postDTO.getPostLong())
						.postContent(postDTO.getPostContent())
						.likeCnt(postDTO.getLikeCnt())
						.postImg(postDTO.getPostImg())
						.build();
		return post;
	}
}
