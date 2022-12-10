package com.spring.gugu.service;

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
import com.spring.gugu.dto.LikeTableDTO;
import com.spring.gugu.dto.PostDTO;
import com.spring.gugu.entity.LikeTable;
import com.spring.gugu.entity.Post;
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
	public PageResultDTO<PostDTO, Post> getList(PageRequestDTO requestDTO) {
		// pageable 객체 생성
		Pageable pageable = requestDTO.getPageable();
		// Post타입의 Page 객체 생성
		Page<Post> result = postRepo.findAll(pageable);
		// Post 타입을 PostDTO 타입으로 변경해 저장하는 function 정의
		Function<Post, PostDTO> fn = (post -> post.entityToDTO(post));
		// PageResultDTO 객체에 페이지에 담을 내용인 result값과 EntitytoDTO변경을 위한 function을 전달
		return new PageResultDTO<PostDTO, Post>(result, fn);
	}

	@Override
	public Long save(Post post) {
		return postRepo.save(post).getPostNo();
	}

	@Override
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
      // TODO Auto-generated method stub
      return postRepo.getById(postNo);
   }
   
   @Override
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
	public Long addLike(Long postNo, Long userId, int afterLike) {
//		System.out.println("likeDTO : "+likeTableDTO);
//		Long likeNo = likeRepo.saveAndFlush(LikeTableDTO.dtoToEntity(likeTableDTO)).getLikeNo();
//		System.out.println("likeNo : " + likeNo);
//		Post post = postRepo.getById(likeRepo.getById(likeNo).getPost().getPostNo());
		LikeTable likeTable = LikeTable.builder()
										.post(postRepo.getById(postNo))
										.user(userRepo.getById(userId))
										.afterLike(afterLike)
										.build();
		
		
		Post post = likeRepo.saveAndFlush(likeTable).getPost();
		post.addLike();
		postRepo.save(post);
		
		Long likeCnt = post.getLikeCnt();
		
		return likeCnt;
	}

	
}
