package com.spring.gugu.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.gugu.entity.User;

//기본적인 CRUD 함수를 가지고 있음 -> DAO 클래스 개념임!!
//JpaRepository를 상속했기 때문에 @Repository 어노테이션 불필요
@Repository
public interface KakaoRepository extends JpaRepository<User, Long>{
	
	// JPA findBy 규칙
	// select * from user_master where kakao_email = ?
//	public User findByKakaoEmail(Long kakaoId);

	public User getUserByKakaoId(Long kakaoId);

	// kakaoId로 user data 불러오기
	public User findByKakaoId(Long kakaoId);
	
	// nickame으로 user data 불러오기
	public List<User> findAllBykakaoNicknameContaining(String nickname);
}
