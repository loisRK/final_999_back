package com.spring.diary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.diary.entity.KakaoEntity;

//기본적인 CRUD 함수를 가지고 있음 -> DAO 클래스 개념임!!
//JpaRepository를 상속했기 때문에 @Repository 어노테이션 불필요
@Repository
public interface KakaoRepository extends JpaRepository<KakaoEntity, Long>{
	
	// JPA findBy 규칙
	// select * from user_master where kakao_email = ?
	public KakaoEntity findByKakaoEmail(String kakaoEmail);
	
	public KakaoEntity findByKakaoCode(Long kakaoCode);
}
