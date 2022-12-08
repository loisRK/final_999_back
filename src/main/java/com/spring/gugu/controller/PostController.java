package com.spring.gugu.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.DynamicUpdate;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
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
	
	// 포스트 작성하기
	@PostMapping("/post")
	public void insertPost(@RequestParam("content") String content, 
				@RequestParam(name="files", required = false) List<MultipartFile> files) {
		
		if (files != null) {
			for(MultipartFile file : files) {
				System.out.println(file.getSize());
				String originalFileName = file.getOriginalFilename();
				String fileName = UUID.randomUUID().toString() + originalFileName;
				String savePath = System.getProperty("user.dir") + "\\files\\";
				String fileFullPath = savePath + fileName;
				// front에서 파일 저장 경로를 src 값으로 넣을 수 있도록 주소 저장
				System.out.println("fileFullPath"+fileFullPath);
				
				
				if(file.getSize() == 0) {
					System.out.println("file없음");
					break;
				}else {
					// post 객체에 값 저장
					Post post = Post.builder()
							.postContent(content)
							.postImg(fileFullPath)
							.build();
					
					postRepo.save(post);
					System.out.println("post 저장 완료(postNo) : " + postRepo.save(post).getPostNo());
					
					// 디렉토리에 파일 저장
					try {
						file.transferTo(new File(savePath + "\\" + fileName));
					} catch (IllegalStateException | IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
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
//		PageResultDTO pageResultDTO2 = diaryService.getList(requestDTO2);
//		System.out.println(pageResultDTO2);
//		return pageResultDTO2;
		return null;
	}
	

}
