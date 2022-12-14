package com.spring.gugu.dto;

import java.sql.Timestamp;

import com.spring.gugu.entity.LikeTable;
import com.spring.gugu.entity.Post;
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
@ToString(exclude = {"postDTO"})
public class LikeTableDTO {
	
	private Long likeNo;
//	private Long userId;
//	private Long postNo;
	private UserDTO userDTO;
	private PostDTO postDTO;
	private int afterlike;
	
	public static LikeTable dtoToEntity(LikeTableDTO likeTableDTO) {
		LikeTable likeTable = LikeTable.builder()
						.likeNo(likeTableDTO.getLikeNo())
//						.userId(likeTableDTO.getUserId())
//						.postNo(likeTableDTO.getPostNo())
						.user(UserDTO.dtoToEntity(likeTableDTO.getUserDTO()))
						.post(PostDTO.dtoToEntity(likeTableDTO.getPostDTO()))
						.afterLike(likeTableDTO.getAfterlike())
						.build();
		return likeTable;
	}

}
