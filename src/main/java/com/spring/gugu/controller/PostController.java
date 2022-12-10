package com.spring.gugu.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.DynamicUpdate;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.spring.gugu.common.dto.PageRequestDTO;
import com.spring.gugu.common.dto.PageResultDTO;
import com.spring.gugu.dto.FileDTO;
import com.spring.gugu.entity.FileEntity;
import com.spring.gugu.entity.Post;
import com.spring.gugu.repository.PostRepository;
import com.spring.gugu.service.FileService;
import com.spring.gugu.service.PostServiceImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController		// 페이지 전환이 필요없으므로 restController 사용
@RequestMapping(value = "/api")
@CrossOrigin(origins = {"http://localhost:3000"})
@DynamicUpdate
@RequiredArgsConstructor
public class PostController {
	
	final PostServiceImpl postService;
	
	// 포스트 작성하기
	@PostMapping("/post")
	public Long insertPost(@RequestParam("postContent") String content, 
				@RequestParam("postLat") String postLat,
				@RequestParam("postLong") String postLong,
				@RequestParam(name="files", required = false) List<MultipartFile> files) {
		
		Long postNo = null;
		String fileFullPath = null;
		
		for(MultipartFile file : files) {
			// front에서 파일 저장 경로를 src 값으로 넣을 수 있도록 주소 저장
			String fileName = UUID.randomUUID().toString() + file.getOriginalFilename();
			
			if(file.getSize() == 0) {
				System.out.println("file없음");
				break;
			}else {
				// post 객체에 값 저장
				Post post = Post.builder()
						.postLat(Double.parseDouble(postLat))
						.postLong(Double.parseDouble(postLong))
						.postContent(content)
						.postImg(fileName)
						.build();
				postNo = postService.save(post);
				
				try {
					// 디렉토리에 파일 저장 - react>public>img 폴더를 절대경로로 지정
					file.transferTo(new File("C:\\dev\\gugu\\final_999_react\\public\\img\\" + fileName));
				} catch (IllegalStateException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		System.out.println("post 저장 완료(postNo) : " + postNo);
		return postNo;
	}
	
	
	// 모든 포스트 불러오기
	@GetMapping("/postPage")
	public PageResultDTO postPage(@RequestParam("page") int pageNo, 
			@RequestParam("size") int size) {
		// pagination을 위한 pageable 객체 생성
		PageRequestDTO requestDTO2 = PageRequestDTO.builder()
				.page(pageNo)
				.size(size)
				.build();
		// pageable 객체에 넣은 post 전체 데이터
		PageResultDTO pageResultDTO2 = postService.getList(requestDTO2);
		System.out.println("pageREsult:"+pageResultDTO2);
		return pageResultDTO2;
	}
	
	// 포스트 제거하기
	@DeleteMapping("/postDelete")
	public void deleteDiary(@RequestParam("postNo") Long postNo) {
		postService.deletePost(postNo);
	}

}
