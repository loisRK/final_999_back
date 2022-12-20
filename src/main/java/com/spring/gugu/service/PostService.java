package com.spring.gugu.service;

import java.util.List;

import java.util.NoSuchElementException;

import com.spring.gugu.common.dto.PageRequestDTO;
import com.spring.gugu.common.dto.PageResultDTO;
import com.spring.gugu.dto.LikeTableDTO;
import com.spring.gugu.dto.PostDTO;
import com.spring.gugu.dto.PostLikeDTO;
import com.spring.gugu.entity.Post;
import com.spring.gugu.entity.User;

public interface PostService {
	
	public PageResultDTO<PostDTO, Post> getList(PageRequestDTO requestDTO);
//	public PageResultDTO<PostLikeDTO, Post> getPostLike(PageRequestDTO requestDTO, Long loginId);
	public PageResultDTO<PostLikeDTO, Post> getPostLike(PageRequestDTO requestDTO, Long loginId, String nickname); // 전체포스트 infinity scroll
	public PageResultDTO<PostLikeDTO, Post> getPostLike(PageRequestDTO requestDTO, Long loginId); // mypage 포스트 infinity scroll

	public Long save(Post post);
	
	public PostDTO getPostByNo(Long postNo);
	
	public List<PostDTO> findAll();
	
	void postDTOUpdate(Long postNo, String content, String postImg);

	public void deletePost(Long postNo);

	// 좋아요 추가
	public Long addLike(Long postNo, Long userId, int afterLike);
	
	public Long getLike(Long postNo, Long userId);

	public List<PostDTO> getPostsByUserId(Long userId);

	public Long addLikeCnt(Long postNo, Long userId, int afterLike);

}
