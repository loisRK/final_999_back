package com.spring.gugu.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.parser.ParseException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.spring.gugu.dto.UserDTO;
import com.spring.gugu.entity.User;
import com.spring.gugu.model.KakaoProfile;
import com.spring.gugu.model.OauthToken;

public interface KakaoService {

	public OauthToken getAccessToken(String code);

	public KakaoProfile findProfile(String token);

	public ArrayList<User> checkMember(String access_token);
	
//	public ArrayList<String> SaveUserAndGetToken(String access_token);
	public String SignupAndGetToken(String token, String nickname, String gender, String age, List<MultipartFile> files);
	
//	public ResponseEntity<String> logout(HttpServletRequest request);
	public ResponseEntity<String> logout2(HttpServletRequest request);

//	public User getUserById(Long userId);
	
	public String createToken(User user, String token);

	public UserDTO getUser(HttpServletRequest request);

	public User getUserById(Long kakaoId);
	
	public void userUpdate(Long userId, String email, String nickname, String fileName);

	public ResponseEntity<String> withdrawMember(HttpServletRequest request);

	public void userDelete(Long userId);

	public int updatePostCnt(Long kakaoId, int addCnt);

}
