package com.spring.gugu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.spring.gugu.entity.LikeTable;
import com.spring.gugu.entity.Post;
import com.spring.gugu.entity.User;

@Repository
public interface LikeRepository extends JpaRepository<LikeTable, Long>{

//	@Query(value="select ifnull( (select afterLike from LikeTable where postNo = :postNo and userId = :userId), 0)")
//	Long getAfterlikeByPostAndUser(@Param("postNo") Long postNo, @Param("userId") Long userId);
	@Query(value="select l.afterLike from LikeTable l where l.post = :post and l.user = :user")
	Long getAfterlikeByPostAndUser(@Param("post") Post post, @Param("user") User user);
	

}
