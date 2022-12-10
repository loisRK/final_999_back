package com.spring.gugu.service;

import java.util.List;

import com.spring.gugu.common.dto.PageRequestDTO;
import com.spring.gugu.common.dto.PageResultDTO;
import com.spring.gugu.dto.PostDTO;
import com.spring.gugu.entity.Post;

public interface PostService {
	
	public PageResultDTO<PostDTO, Post> getList(PageRequestDTO requestDTO);

	public Long save(Post post);
	
	public PostDTO getPostByNo(Long postNo);
	
	void postDTOUpdate(Long postNo, String content, String postImg);
	
	public List<PostDTO> findAll();

	

	public void deletePost(Long postNo);
}
