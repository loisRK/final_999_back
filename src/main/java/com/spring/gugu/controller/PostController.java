package com.spring.gugu.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.spring.gugu.common.dto.PageRequestDTO;
import com.spring.gugu.common.dto.PageResultDTO;
import com.spring.gugu.config.jwt.JwtProperties;
import com.spring.gugu.dto.UserDTO;
//import com.spring.gugu.entity.Like;
import com.spring.gugu.entity.Post;
import com.spring.gugu.entity.User;
import com.spring.gugu.service.FileServiceImpl;
import com.spring.gugu.service.KakaoServiceImpl;
import com.spring.gugu.service.PostServiceImpl;

import lombok.RequiredArgsConstructor;

@RestController		// 페이지 전환이 필요없으므로 restController 사용
@RequestMapping(value = "/api")
@CrossOrigin(origins = {"http://localhost:3000"})
@DynamicUpdate
@RequiredArgsConstructor
public class PostController {
	
	final PostServiceImpl postService;
	final KakaoServiceImpl userService;
	final FileServiceImpl fileService;
	
	// 포스트 작성하기
	@PostMapping("/post")
	public Long insertPost(
						@RequestParam("postContent") String content, 
						@RequestParam("postLat") String postLat,
						@RequestParam("postLong") String postLong,
						@RequestParam(name="files", required = false) List<MultipartFile> files,
						HttpServletRequest request) {
		
		Long postNo = null;
		
		// localStroage의 user_id로  user 정보 get 
		Long kakaoId = (Long)request.getAttribute("userCode");
		System.out.println("kakao ID : " + kakaoId);
		UserDTO userDTO = userService.getUser(request);
//		System.out.println("----------user--------" + user);
		
		// file이 있는 경우 file upload 후 fileName 반환
		String fileName = fileService.uploadFile(files);	
		
		// like entity 에서 like 개수 조회해 Post 객체에 넣어주는 likeService 필요
		
		
		Post post = Post.builder()
				.user(UserDTO.dtoToEntity(userDTO))
				.postLat(Double.parseDouble(postLat))
				.postLong(Double.parseDouble(postLong))
				.postContent(content)
				.postImg(fileName)
				.build();
		
		postNo = postService.save(post);
		System.out.println("post 저장 완료(postNo) : " + postNo);
		
		return postNo;
	}
	
	
	// 모든 포스트 불러오기
	@GetMapping("/postPage")
	public PageResultDTO postPage(@RequestParam("page") int pageNo, @RequestParam("size") int size) {
		
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

//	@GetMapping("/clickLike")
//	public int countLike(Long kakaoId, Long postNo, int afterLike) {
//		kakaoId = 2560503522L;
//		postNo = 10L;
//		
//		User user = userService.getUserById(kakaoId);
//		Post post = postService.getById(postNo);
//		
//		Like like = Like.likeId(user, post);
//		
//		return 0;
//	}
	

}
