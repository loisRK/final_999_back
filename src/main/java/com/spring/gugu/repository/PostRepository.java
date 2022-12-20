package com.spring.gugu.repository;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.spring.gugu.entity.Post;
import com.spring.gugu.entity.User;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>{
	
	@Query(value = "SELECT p FROM Post p WHERE p.user = :user")
	List<Post> findAllByUser(@Param("user") User user); 
	
	@Query(value = "select p from Post p where p.user in :user")
	Page<Post> findAllByUser(@Param("user") List<User> users, Pageable pageable);
	
	@Query(value = "select p from Post p where p.user = :user")
	Page<Post> findAllByUser(@Param("user") User user, Pageable pageable);
//	List<PostLikeDTO>
}
