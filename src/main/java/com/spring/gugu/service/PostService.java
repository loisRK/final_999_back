package com.spring.gugu.service;

import com.spring.gugu.common.dto.PageRequestDTO;
import com.spring.gugu.common.dto.PageResultDTO;
import com.spring.gugu.dto.PostDTO;
import com.spring.gugu.entity.Post;

public interface PostService {
	
	public PageResultDTO<PostDTO, Post> getList(PageRequestDTO requestDTO);

	public Long save(Post post);

	public void deletePost(Long postNo);
}
