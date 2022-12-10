package com.spring.gugu.service;

import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.spring.gugu.common.dto.PageRequestDTO;
import com.spring.gugu.common.dto.PageResultDTO;
import com.spring.gugu.dto.PostDTO;
import com.spring.gugu.entity.Post;
import com.spring.gugu.repository.PostRepository;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	PostRepository postRepo;
	
	@Override
	public PageResultDTO<PostDTO, Post> getList(PageRequestDTO requestDTO) {
		// pageable 객체 생성
		Pageable pageable = requestDTO.getPageable();
		// Post타입의 Page 객체 생성
		Page<Post> result = postRepo.findAll(pageable);
		System.out.println("Page<Post>:" + result);
		// Post 타입을 PostDTO 타입으로 변경해 저장하는 function 정의
		Function<Post, PostDTO> fn = (post -> Post.entityToDTO(post));
		// PageResultDTO 객체에 페이지에 담을 내용인 result값과 EntitytoDTO변경을 위한 function을 전달
		return new PageResultDTO<PostDTO, Post>(result, fn);
	}

	@Override
	public Long save(Post post) {
		return postRepo.save(post).getPostNo();
	}

	public Post getById(Long postNo) {
		// TODO Auto-generated method stub
		return postRepo.getById(postNo);
	}

	
}
