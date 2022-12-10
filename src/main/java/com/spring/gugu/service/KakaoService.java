package com.spring.gugu.service;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.parser.ParseException;
import org.springframework.http.ResponseEntity;

import com.spring.gugu.dto.UserDTO;
import com.spring.gugu.entity.User;
import com.spring.gugu.model.KakaoProfile;
import com.spring.gugu.model.OauthToken;

public interface KakaoService {

	public OauthToken getAccessToken(String code);

	public KakaoProfile findProfile(String token);

	public String SaveUserAndGetToken(String access_token);

	
	public ResponseEntity<String> logout(HttpServletRequest request);

//	public User getUserById(Long userId);

	public UserDTO getUser(HttpServletRequest request);

	public User getUserById(Long kakaoId);

}
