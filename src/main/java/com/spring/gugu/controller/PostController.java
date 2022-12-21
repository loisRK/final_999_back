package com.spring.gugu.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.spring.gugu.common.dto.PageRequestDTO;
import com.spring.gugu.common.dto.PageResultDTO;
import com.spring.gugu.dto.PostDTO;
import com.spring.gugu.dto.UserDTO;
//import com.spring.gugu.entity.Like;
import com.spring.gugu.entity.Post;
import com.spring.gugu.entity.User;
import com.spring.gugu.service.FileServiceImpl;
import com.spring.gugu.service.KakaoServiceImpl;
import com.spring.gugu.service.PostServiceImpl;
import com.spring.gugu.service.S3Uploader;

import lombok.RequiredArgsConstructor;

@RestController // 페이지 전환이 필요없으므로 restController 사용
@RequestMapping(value = "/api")
@CrossOrigin(origins = { "http://localhost:3000" })
@DynamicUpdate
@RequiredArgsConstructor
public class PostController {

	final PostServiceImpl postService;
	final KakaoServiceImpl userService;
	final FileServiceImpl fileService;
	final S3Uploader s3Uploader;
	
	// 포스트 작성하기
	@PostMapping("/post")
	public Long insertPost(
						@RequestParam("postContent") String content, 
						@RequestParam("postLat") String postLat,
						@RequestParam("postLong") String postLong,
//						@RequestParam(name="files", required = false) List<MultipartFile> files,
						@RequestParam(name = "files", required = false) MultipartFile file,
						HttpServletRequest request) {
		
		Long postNo = null;
		String fileName = "";

		// localStroage의 user_id로 user 정보 get
		UserDTO userDTO = userService.getUser(request);
		System.out.println("######### insertDiary userDTO : " + userDTO.toString());
		

		if (file != null && file.getSize() != 0) {
			try {
				// s3 file 링크로 fileName 받아와서 postImg data로 저장하면 src로 걍 링크를 긁어오면 화면에 출력됨
				fileName = s3Uploader.uploadFiles(file, "gugu-post");
				System.out.println("s3 file url : "+fileName);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		

		System.out.println("####"+content+"####");
		// 포스팅
		if(content != null && content != "") {
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
	        
	        // 사용자의 총 포스팅 개수 +1 추가
	        userService.updatePostCnt(userDTO.getKakaoId(), 1);
	    }
//		// post 객체 생성
//		Post post = Post.builder()
//				.user(UserDTO.dtoToEntity(userDTO))
//				.postLat(Double.parseDouble(postLat))
//				.postLong(Double.parseDouble(postLong))
//				.postContent(content)
//				.likeCnt(0L)	// 처음 게시되는 글이므로 0으로 초기값 설정
//				.postImg(fileName)
//				.build();
		
//        postNo = postService.save(post);
//        System.out.println("post 저장 완료(postNo) : " + postNo);

		return postNo;
	}

	// 모든 포스트 불러오기
	@GetMapping("/postPage")
	public PageResultDTO postPage(@RequestParam("page") int pageNo, @RequestParam("size") int size) {

		// pagination을 위한 pageable 객체 생성
		PageRequestDTO requestDTO2 = PageRequestDTO.builder().page(pageNo).size(size).build();

		// pageable 객체에 넣은 post 전체 데이터
		PageResultDTO pageResultDTO2 = postService.getList(requestDTO2);
		System.out.println("pageREsult:" + pageResultDTO2);

		return pageResultDTO2;
	}

	// 검색 기능 추가
	@GetMapping("/postLikePage")
	public PageResultDTO getPostLike(@RequestParam("page") int pageNo, @RequestParam("size") int size, 
			@RequestParam("loginId") Long loginId, @RequestParam("searchId") String searchNickname) {
		
		System.out.println("######################searchId" + searchNickname);
		
		// pagination을 위한 pageable 객체 생성
		PageRequestDTO requestDTO2 = PageRequestDTO.builder().page(pageNo).size(size).build();

		// pageable 객체에 넣은 post 전체 데이터
		PageResultDTO pageResultDTO2 = postService.getPostLike(requestDTO2, loginId, searchNickname);
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
			@RequestParam("stringFile") String fileImg,
			@RequestParam(name = "files", required = false) MultipartFile file) {

		System.out.println("#################포스트 수정");

		String fileName = fileImg;
		
		if (file != null && file.getSize() != 0) {
			try {
					// s3 file 링크로 fileName 받아와서 postImg data로 저장하면 src로 걍 링크를 긁어오면 화면에 출력됨
					fileName = s3Uploader.uploadFiles(file, "gugu-post");
					System.out.println("s3 file url : "+fileName);
			} catch (IOException e) {
				e.printStackTrace();
			}		
		}
		
		postService.postDTOUpdate(postNo, content, fileName);
		
	}

	
	// 포스트 제거하기
	@DeleteMapping("/postDelete")
	public void deleteDiary(@RequestParam("postNo") Long postNo) {

		Post post = postService.getById(postNo);
		String postImgLink = post.getPostImg();
		
		// s3 delete code 실행 안됨
//		if(postImgLink != null && postImgLink != "") {
//			System.out.println("deleteTEST#############"+postService.getById(postNo).getPostImg());
//			s3Uploader.deleteFile(postService.getById(postNo).getPostImg());
//		}
//		
		System.out.println("postNo : "+postNo);
		postService.deletePost(postNo);
		
		// 포스팅 제거하면 사용자 포스팅 총 개수에서 -1하기
		userService.updatePostCnt(post.getUser().getKakaoId(), 0);
	}


	// 좋아요 누르기 - 근영
	@PostMapping("/addLikeCnt")
	public Long addLikeCnt(@RequestParam("postNo") String postNo, @RequestParam("userId") String userId,
			@RequestParam("afterLike") int afterLike) {
		System.out.println("---");
		System.out.println("##################### " + postNo + " " + userId + " " + afterLike);
		Long likeCnt = postService.addLikeCnt(Long.parseLong(postNo), Long.parseLong(userId), afterLike);
		System.out.println("######## LIKECNT : " + afterLike + " " + likeCnt);

		return likeCnt;
	}

	// 해당 유저의 좋아요 정보 가져오기
	@GetMapping("/getLike")
	public Long getLike(@RequestParam("postNo") Long postNo, @RequestParam("userId") Long userId) {
		System.out.println("################# " + postNo + userId);
		return postService.getLike(postNo, userId);
	}


    // 모든 포스트 불러오기 
    @GetMapping("/postList")
    public List<PostDTO> getAllposts() {
       List<PostDTO> allPosts = postService.findAll();
       System.out.println("####################allPosts" + allPosts);
       return allPosts;
    }	
  
  
    // 특정 유저의 모든 포스트 불러오기 
    @GetMapping("/userPosts/{userId}")
    public List<PostDTO> getUserposts(@PathVariable("userId") Long userId) {
       List<PostDTO> allPosts = postService.getPostsByUserId(userId);
       System.out.println("####################유저 포스트" + allPosts);
       return allPosts;
    }	
    
    
    ///////////////////TagLibrary TEST////////////////////
    @PostMapping("/tagTest")
    public void addTag(
		  @RequestParam("tag1") String tag1, 
		  @RequestParam("tag2") String tag2, 
		  @RequestParam("tag3") String tag3, 
  		  @RequestParam("roomNo") String roomNo) {
	  
    }
    
    // 마이페이지용 infinity scroll 조회
 	@GetMapping("/mypagePosts")
 	public PageResultDTO getPostLike(@RequestParam("page") int pageNo, @RequestParam("size") int size, 
 			@RequestParam("loginId") Long loginId) {
 		
 		// pagination을 위한 pageable 객체 생성
 		PageRequestDTO requestDTO2 = PageRequestDTO.builder().page(pageNo).size(size).build();

 		// pageable 객체에 넣은 post 전체 데이터
 		PageResultDTO pageResultDTO2 = postService.getPostLike(requestDTO2, loginId);
 		System.out.println("pageREsult:"+pageResultDTO2);

 		return pageResultDTO2;
 	}

}
