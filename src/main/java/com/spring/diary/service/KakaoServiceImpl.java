package com.spring.diary.service;

import java.net.URI;
import java.util.Base64;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.mapping.Map;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.diary.config.jwt.JwtProperties;
import com.spring.diary.entity.KakaoEntity;
import com.spring.diary.model.KakaoProfile;
import com.spring.diary.model.OauthToken;
import com.spring.diary.repository.KakaoRepository;

@Service
public class KakaoServiceImpl implements KakaoService {
	
	@Autowired
	KakaoRepository kakaoRepo;
	
    //환경 변수 가져오기
    @Value("${kakao.clientId}")
    String client_id;

	@Override
	public OauthToken getAccessToken(String code) {
		
		// 통신에 유용한 클래스
		RestTemplate rt = new RestTemplate();
		
		// http 헤더
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
		
		// http 바디
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("grant_type", "authorization_code");
		params.add("client_id", client_id);
		params.add("redirect_uri", "http://localhost:3000/oauth/callback/kakao");
		params.add("code", code);
//		params.add("client_secret", "{시크릿 키}");		// 생략 가능!
		
		// http 헤더와 바디를 합치기 위한 엔터티
		HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest =
				new HttpEntity<>(params, headers);
		
		// 응답받는 값이 Json 형식이기 때문에 ResponseEntity 객체를 String 형만 받도록 생성
		ResponseEntity<String> accessTokenResponse = rt.exchange(
				 "https://kauth.kakao.com/oauth/token",
				 HttpMethod.POST,
				 kakaoTokenRequest,
				 String.class
		);
		
		// String으로 받은 Json 형식의 데이터를 ObjectMapper 라는 클래스를 사용해 객체로 변환
		ObjectMapper objectMapper = new ObjectMapper();
		OauthToken oauthToken = null;
		try {
			oauthToken = objectMapper.readValue(accessTokenResponse.getBody(), OauthToken.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		return oauthToken;
	}
	

	// 응답 받은 Json 데이터와 정확히 일치하는 KakaoProfile 클래스를 만든다
	@Override
	public KakaoProfile findProfile(String token) {
		
		// 통신에 필요한 RestTemplate 객체를 만든다
		RestTemplate rt = new RestTemplate();
		
		// http 헤더
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer " + token); // 헤더에는 발급받은 엑세스 토큰을 넣어 요청해야한다
		headers.add("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
		
		// HttpHeader 정보를 http 엔터티에 담아준다
		HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest =
				new HttpEntity<>(headers);
		
		// HttP 요청 (POST 방식) 후, response 변수에 응답을 받음
		// 해당 주소로 Http 요청을 보내 String 변수에 응답을 받는다
		ResponseEntity<String> kakaoProfileResponse = rt.exchange(
				"https://kapi.kakao.com/v2/user/me",
				HttpMethod.POST,
				kakaoProfileRequest,
				String.class
		);
		System.out.println("########################### " + kakaoProfileResponse);
		
		// Json 응답을 KakaoProfile 객체로 변환해 리턴
		ObjectMapper objectMapper = new ObjectMapper();
		KakaoProfile kakaoProfile = null;
		try {
			kakaoProfile = objectMapper.readValue(kakaoProfileResponse.getBody(), KakaoProfile.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		return kakaoProfile;
	}
	
	@Override
	public String SaveUserAndGetToken(String token) {
		KakaoProfile profile = findProfile(token);
		
		KakaoEntity user = kakaoRepo.findByKakaoEmail(profile.getKakao_account().getEmail());
		if(user == null) {
			user = KakaoEntity.builder()
							  .kakaoId(profile.getId())
							  .kakaoProfileImg(profile.getKakao_account().getProfile().getProfile_image_url())
							  .kakaoNickname(profile.getKakao_account().getProfile().getNickname())
							  .kakaoEmail(profile.getKakao_account().getEmail())
							  .kakaoRole("ROLE_USER")
							  .build();
			
			kakaoRepo.save(user);
		}
		return createToken(user, token);
	}
	
	public String createToken(KakaoEntity user, String token) {
		System.out.println("######## CREATETOKEN TOKEN : " + token);
		
		String jwtToken = JWT.create()
				
				.withSubject(user.getKakaoEmail())
				.withExpiresAt(new Date(System.currentTimeMillis() + JwtProperties.EXPIRATION_TIME))
				
				.withClaim("id", user.getKakaoCode())
				.withClaim("nickname", user.getKakaoNickname())
				.withClaim("access_token", token)
				
				.sign(Algorithm.HMAC512(JwtProperties.SECRET));
		
		return jwtToken;
	}
	
	@Override
	public KakaoEntity getUser(HttpServletRequest request) {
        Long kakaoCode = (Long) request.getAttribute("userCode");
        
        KakaoEntity user = kakaoRepo.findByKakaoCode(kakaoCode);

        return user;
	}

	@Override
	public ResponseEntity<String> logout(HttpServletRequest request) {
		
		// request 안에 들어있는 jwt 토큰의 payload 부분에서 accessToken 정보를 불러옴
		String token = (String) request.getAttribute("accessToken");
		
		// 통신에 필요한 RestTemplate 객체를 만든다
		RestTemplate rt = new RestTemplate();
		
		// http 헤더
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer " + token); // 헤더에는 발급받은 엑세스 토큰을 넣어 요청해야한다
		headers.setLocation(URI.create("/"));
		
		// HttpHeader 정보를 http 엔터티에 담아준다
		HttpEntity<MultiValueMap<String, String>> kakaoLogoutRequest =
				new HttpEntity<>(headers);
		System.out.println("############ HEADERS : " + headers);
		
		/*
		 * - HttP 요청 (POST 방식) 후, response 변수에 응답을 받음
		 * - 해당 주소로 Http 요청을 보내 String 변수에 응답을 받는다
		 * - access token 방식으로 request를 보내 logout을 실행하는 API 호출 
		 */
		ResponseEntity<String> kakaoLogoutResponse = rt.exchange(

//				"https://kapi.kakao.com/v1/user/unlink",		// 동의 전으로 돌아가기
				"https://kapi.kakao.com/v1/user/logout",		// 그냥 로그아웃하기 
				HttpMethod.POST,
				kakaoLogoutRequest,
				String.class
		);
		System.out.println("############ kakaoLogoutResponse : " + kakaoLogoutResponse);
		
		return kakaoLogoutResponse;
	}
}
