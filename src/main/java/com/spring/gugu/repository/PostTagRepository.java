package com.spring.gugu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.gugu.entity.PostTag;
import com.spring.gugu.entity.PostTagId;

@Repository
public interface PostTagRepository extends JpaRepository<PostTag, PostTagId> {

}
