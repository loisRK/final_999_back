package com.spring.diary.service;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.parser.ParseException;
import org.springframework.http.ResponseEntity;

import com.spring.diary.entity.KakaoEntity;
import com.spring.diary.model.KakaoProfile;
import com.spring.diary.model.OauthToken;

public interface KakaoService {

	public OauthToken getAccessToken(String code);

	public KakaoProfile findProfile(String token);

	public String SaveUserAndGetToken(String access_token);

	public KakaoEntity getUser(HttpServletRequest request);
	
	public ResponseEntity<String> logout(HttpServletRequest request);
}
