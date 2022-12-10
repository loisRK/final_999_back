package com.spring.gugu.dto;

import java.time.LocalDateTime;
import java.util.List;

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
	private int likeCnt;
	private String postImg;
	
	public static Post dtoToEntity(PostDTO postDTO) {
		Post post = Post.builder()
						.postContent(postDTO.getPostContent())
						.postImg(postDTO.getPostImg())
						.build();
		return post;
	}
}
