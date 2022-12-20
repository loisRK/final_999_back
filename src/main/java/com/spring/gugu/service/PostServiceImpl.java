package com.spring.gugu.service;


import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.spring.gugu.common.dto.PageRequestDTO;
import com.spring.gugu.common.dto.PageResultDTO;
import com.spring.gugu.dto.PostDTO;
import com.spring.gugu.dto.PostLikeDTO;
import com.spring.gugu.entity.LikeTable;
import com.spring.gugu.entity.Post;
import com.spring.gugu.entity.User;
import com.spring.gugu.repository.KakaoRepository;
import com.spring.gugu.repository.LikeRepository;
import com.spring.gugu.repository.PostRepository;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	PostRepository postRepo;
	
	@Autowired
	LikeRepository likeRepo;
	
	@Autowired
	KakaoRepository userRepo;
	
	@Override
	@Transactional
	public PageResultDTO<PostDTO, Post> getList(PageRequestDTO requestDTO) {
		// pageable 객체 생성
		Pageable pageable = requestDTO.getPageable();
		// Post타입의 Page 객체 생성
		Page<Post> result = postRepo.findAll(pageable);
		// Post 타입을 PostDTO 타입으로 변경해 저장하는 function 정의
		Function<Post, PostDTO> fn = (post -> Post.entityToDTO(post));
		// PageResultDTO 객체에 페이지에 담을 내용인 result값과 EntitytoDTO변경을 위한 function을 전달
		return new PageResultDTO<PostDTO, Post>(result, fn);
	}
	
//	@Override
//	@Transactional
//	public PageResultDTO<PostLikeDTO, Post> getPostLike(PageRequestDTO requestDTO, Long loginId) {
//		// pageable 객체 생성
//		System.out.println("inside service");
//		Pageable pageable = requestDTO.getPageable();
//		// Post타입의 Page 객체 생성
//		Page<Post> result = postRepo.findAll(pageable);
//		System.out.println("######### Page<Post> : " + result);
//		// Post 타입을 PostDTO 타입으로 변경해 저장하는 function 정의
//		Function<Post, PostLikeDTO> fn = (post -> PostLikeDTO.fromEntities(post, getLoginLikes(post, loginId)));
//		// PageResultDTO 객체에 페이지에 담을 내용인 result값과 EntitytoDTO변경을 위한 function을 전달
//		return new PageResultDTO<PostLikeDTO, Post>(result, fn);
//	}
	
	
	@Override
	@Transactional
	public PageResultDTO<PostLikeDTO, Post> getPostLike(PageRequestDTO requestDTO, Long loginId, String nickname) {
		// pageable 객체 생성
		System.out.println("inside service");
		Pageable pageable = requestDTO.getPageable();
		Page<Post> result = null;
		System.out.println("################"+nickname);
		// searchId == null
		if(nickname.equals("null")) {
			// Post타입의 Page 객체 생성
			result = postRepo.findAll(pageable);
			
		} else {
			List<User> users = userRepo.findAllBykakaoNicknameContaining(nickname);
			System.out.println("*****************user" + users);
			result = postRepo.findAllByUser(users, pageable);
		}
		
		System.out.println("######### Page<Post> : " + result);
		// Post 타입을 PostDTO 타입으로 변경해 저장하는 function 정의
		Function<Post, PostLikeDTO> fn = (post -> PostLikeDTO.fromEntities(post, getLoginLikes(post, loginId)));
		// PageResultDTO 객체에 페이지에 담을 내용인 result값과 EntitytoDTO변경을 위한 function을 전달
		return new PageResultDTO<PostLikeDTO, Post>(result, fn);
	}

	public int getLoginLikes(Post post, Long loginId) {
		for(LikeTable likeTable : post.getLikes()) {
			if (loginId.equals(likeTable.getUser().getKakaoId())) {
				return likeTable.getAfterLike();
			}
		}
		
		return 0;
	}

	@Override
	@Transactional
	public Long save(Post post) {
		return postRepo.save(post).getPostNo();
	}

	@Override
	@Transactional
	public PostDTO getPostByNo(Long postNo) throws NoSuchElementException{
		Post post = postRepo.findById(postNo).orElseThrow(NoSuchElementException::new);
		PostDTO postDTO = Post.entityToDTO(post);
		return postDTO;
	}

   @Override
   @Transactional
   public void postDTOUpdate(Long postNo, String content, String postImg) throws NoSuchElementException{
      Post post = postRepo.findById(postNo).orElseThrow(NoSuchElementException::new);
      post.updatePost(content, postImg);
      System.out.println("#####################  변경 된 내용 : " + post.getPostContent());
   }

   public void deletePost(Long postNo) {
	      postRepo.deleteById(postNo);
	     
	   }
	   
   public Post getById(Long postNo) {
      return postRepo.getById(postNo);
   }

	@Override
	@Transactional
	public Long addLike(Long postNo, Long userId, int afterLike) {
//		System.out.println("likeDTO : "+likeTableDTO);
//		Long likeNo = likeRepo.saveAndFlush(LikeTableDTO.dtoToEntity(likeTableDTO)).getLikeNo();
//		System.out.println("likeNo : " + likeNo);
//		Post post = postRepo.getById(likeRepo.getById(likeNo).getPost().getPostNo());
		
		System.out.println("##################### " + postNo + userId + afterLike);
		LikeTable likeTable = LikeTable.builder()
				.post(postRepo.getById(postNo))
				.user(userRepo.getById(userId))
				.afterLike(afterLike)
				.build();
		
		if(afterLike == 1) {
			Post post = likeRepo.saveAndFlush(likeTable).getPost();
			post.addLike();
			postRepo.save(post);
			
			Long likeCnt = post.getLikeCnt();
			
			return likeCnt;
		} else {
			Post post = likeTable.getPost();
			post.minusLike();
			postRepo.save(post);
			
			Long likeCnt = post.getLikeCnt();
			
			return likeCnt;
		}
		
	}

	
   @Override
   @Transactional
   public List<PostDTO> findAll() {
      List<Post> allPosts = postRepo.findAll();
      
      // Post 타입을 PostDTO로 타입으로 변경
      Function<Post, PostDTO> fn = (post -> post.entityToDTO(post));
      List<PostDTO> allPostDTOs = allPosts.stream()
                                 .map(fn)
                                 .collect(Collectors.toList());
      
      return allPostDTOs;
   }

	@Override
	@Transactional
	public Long getLike(Long postNo, Long userId) {
		
//		Post post = postRepo.getById(postNo);
//		User user = userRepo.getById(userId);
		
		return likeRepo.getAfterlikeByPostAndUser(postNo, userId);
//		return likeRepo.getAfterlikeByPostNoAndUserId(post, user);
  }

	@Override
	@Transactional
	public List<PostDTO> getPostsByUserId(Long userId) {
		User user = userRepo.getById(userId);
		
		List<Post> allPosts = postRepo.findAllByUser(user);
		
		
		Function<Post, PostDTO> fn = (post -> post.entityToDTO(post));
	      List<PostDTO> allPostDTOs = allPosts.stream()
	                                 .map(fn)
	                                 .collect(Collectors.toList());
		
		return allPostDTOs;
	}

	@Override
	@Transactional
	public Long addLikeCnt(Long postNo, Long userId, int afterLike) {
		
		System.out.println("##################### " + postNo + userId + afterLike);
		
		LikeTable likeTable = likeRepo.getByPostNoAndUserId(postNo, userId);
		Post post = postRepo.getById(postNo);
		
		if(likeTable == null) {		// 좋아요 누를 때
			likeTable = LikeTable.builder()
					.post(postRepo.getById(postNo))
					.user(userRepo.getById(userId))
					.afterLike(afterLike)
					.build();
			
			likeRepo.save(likeTable);
			post.addLike();			// 해당 포스트의 전체 likeCnt +1 해주기
		} else {					// 좋아요 해제시킬 때
			if(likeTable.getAfterLike() == 0) {
				post.addLike();
				likeTable.updateLike(1);
			} else {
				post.minusLike();		// 해당 포스트의 전체 likeCnt -1 해주기
				likeTable.updateLike(0);
			}
			
		}
		
		return post.getLikeCnt();
	}
	
	
	@Override
	@Transactional
	public PageResultDTO<PostLikeDTO, Post> getPostLike(PageRequestDTO requestDTO, Long loginId) {
		// pageable 객체 생성
		System.out.println("inside service");
		Pageable pageable = requestDTO.getPageable();
		Page<Post> result = null;
		User users = userRepo.findByKakaoId(loginId);
		System.out.println("*****************user" + users);
		result = postRepo.findAllByUser(users, pageable);
		
		System.out.println("######### Page<Post> : " + result);
		// Post 타입을 PostDTO 타입으로 변경해 저장하는 function 정의
		Function<Post, PostLikeDTO> fn = (post -> PostLikeDTO.fromEntities(post, getLoginLikes(post, loginId)));
		// PageResultDTO 객체에 페이지에 담을 내용인 result값과 EntitytoDTO변경을 위한 function을 전달
		return new PageResultDTO<PostLikeDTO, Post>(result, fn);
	}
}
