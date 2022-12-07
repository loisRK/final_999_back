package com.spring.gugu.controller;

import java.util.List;

import org.hibernate.annotations.DynamicUpdate;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.spring.gugu.entity.Post;
import com.spring.gugu.repository.PostRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController		// 페이지 전환이 필요없으므로 restController 사용
@RequestMapping(value = "/api")
@CrossOrigin(origins = {"http://localhost:3000"})
@DynamicUpdate
@RequiredArgsConstructor
public class PostController {
	
//	@Autowired
	final PostRepository postRepo;
	
	public void insertPost(@RequestParam("post") List<MultipartFile> files, @RequestParam("content") String content) {
		Post post = Post.builder()
						.psotContent(content)
						.build();
		postRepo.save(post);
	}
	

}
