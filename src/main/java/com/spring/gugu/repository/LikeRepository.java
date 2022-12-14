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
	
	
	@Query(value="select ifnull( (select l.after_like from like_table l where l.post_no = :post and l.user_id = :user), 0)", nativeQuery = true)
//	@Query(value="select l.after_like from like_table l where l.post_no = :post and l.user_id = :user", nativeQuery = true)
	Long getAfterlikeByPostAndUser(@Param("post") Long postNo, @Param("user") Long userId);
//	@Query(value="select l.afterLike from LikeTable l where l.postNo = :postNo and l.userId = :userId")
//	Long getAfterlikeByPostNoAndUserId(@Param("postNo") Long postNo, @Param("userId") Long userId);

	@Query(value="select l.like_no, l.after_like, l.post_no, l.user_id from like_table l where l.post_no = :postNo and l.user_id = :userId", nativeQuery = true)
	LikeTable getByPostNoAndUserId(@Param("postNo") Long postNo, @Param("userId") Long userId);

}
