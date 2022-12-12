package com.spring.gugu.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.persistence.Entity;
import javax.servlet.http.HttpServletRequest;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.spring.gugu.common.dto.PageRequestDTO;
import com.spring.gugu.common.dto.PageResultDTO;
import com.spring.gugu.dto.LikeTableDTO;
import com.spring.gugu.dto.PostDTO;
import com.spring.gugu.dto.UserDTO;
//import com.spring.gugu.entity.Like;
import com.spring.gugu.entity.Post;
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
		
		Post post = Post.builder()
				.user(UserDTO.dtoToEntity(userDTO))
				.postLat(Double.parseDouble(postLat))
				.postLong(Double.parseDouble(postLong))
				.postContent(content)
				.likeCnt(0L)	// 처음 게시되는 글이므로 0으로 초기값 설정
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
	
	
	// 포스트 내용 불러오기 
	@GetMapping("/post/{postNo}")
	public PostDTO getPost(@PathVariable Long postNo) {
		System.out.println("포스트 내용 불러오기");
		PostDTO postDTO = null;
		postDTO = postService.getPostByNo(postNo);
		return postDTO;
	}
	
	// 포스트 내용 수정하기 
	@PutMapping("/updatePost/{postNo}")
	public void updatePost(@PathVariable("postNo") Long postNo,
			@RequestParam("content") String content,
			@RequestParam(name = "files", required = false) List<MultipartFile> files) {
		
		System.out.println("#################포스트 수정");
		
		for(MultipartFile file : files) {
			System.out.println("#################포스트 수정");
			System.out.println("content :" + content + "postImg" +files);
			
			// front에서 파일 저장 경로를 src 값으로 넣을 수 있도록 주소 저장
			String fileName = UUID.randomUUID().toString() + file.getOriginalFilename();
			
			
			if(file.getSize() == 0) {
				System.out.println("file없음");
				break;
			}else {
				// post 객체에 값 저장
				postService.postDTOUpdate(postNo, content, fileName);
				
				try {
					// 디렉토리에 파일 저장 - react>public>img 폴더를 절대경로로 지정
					file.transferTo(new File("C:\\dev\\gugu\\final_999_react\\public\\img\\" + fileName));
				} catch (IllegalStateException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	

	// 포스트 제거하기
	@DeleteMapping("/postDelete")
	public void deleteDiary(@RequestParam("postNo") Long postNo) {
		System.out.println("postNo : "+postNo);
		postService.deletePost(postNo);
	}

	
	// 좋아요 누르기
	@PostMapping("/addLike")
	public Long countLike(
			@RequestParam("postNo") String postNo, 
			@RequestParam("userId") String userId,
			@RequestParam("afterLike") int afterLike) {
		
		Long likeCnt = postService.addLike(Long.parseLong(postNo), Long.parseLong(userId), (int)afterLike);
		
		return likeCnt;
	}
	

	  // 모든 포스트 불러오기 
	  @GetMapping("/postList")
	  public List<PostDTO> getAllposts() {
	     List<PostDTO> allPosts = postService.findAll();
	     System.out.println("####################allPosts" + allPosts);
	     return allPosts;
	  }	

}
