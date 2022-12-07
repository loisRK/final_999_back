package com.spring.gugu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.gugu.entity.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>{

}
