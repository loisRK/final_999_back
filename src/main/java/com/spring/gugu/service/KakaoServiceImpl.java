package com.spring.gugu.service;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.json.simple.JSONObject;
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
import org.springframework.web.multipart.MultipartFile;
import com.spring.gugu.service.S3Uploader;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.gugu.config.jwt.JwtProperties;
import com.spring.gugu.dto.UserDTO;
import com.spring.gugu.entity.User;
import com.spring.gugu.model.KakaoProfile;
import com.spring.gugu.model.OauthToken;
import com.spring.gugu.model.RefreshTokenInfo;
import com.spring.gugu.model.TokenInfo;
import com.spring.gugu.repository.KakaoRepository;

@Service
public class KakaoServiceImpl implements KakaoService {
	
	@Autowired
	KakaoRepository kakaoRepo;
	
	@Autowired
	S3Uploader s3Uploader;
	
    //환경 변수 가져오기
	@Value("${kakao.clientId}")
	String client_id;
	
	@Value("${kakao.adminKey}")
	String adminKey;

    
    /*
     * kakao login API 호출 - oauth token 요청 및 반환
     */
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
		params.add("redirect_uri", "http://localhost:3000/checkMember");
//		params.add("redirect_uri", "http://localhost:3000/oauth/callback/kakao");
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
	

	/* 
	 * kakao login API 호출 - user data(kakao profile) 확인 및 반환
	 * 응답 받은 Json 데이터와 정확히 일치하는 KakaoProfile 클래스를 만든다
	 */
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
	
	// 우리 DB에 등록되어 있는 사용자인지 확인
	@Override
	public ArrayList<User> checkMember(String token) {		// token = 액세스 토큰
		// 카카오 서버에게 사용자 정보 요청
		KakaoProfile profile = findProfile(token);
		
		// 우리 DB에서 카카오아이디로 유저 검색
		User userDB = kakaoRepo.findByKakaoId(profile.getId());
		
		// 동의서로 만든 임시 유저 정보
		User userBuild = User.builder()
				  .kakaoId(profile.getId())
				  .kakaoNickname(profile.getKakao_account().getProfile().getNickname())
				  .kakaoProfileImg(profile.getKakao_account().getProfile().getProfile_image_url())
				  .kakaoEmail(profile.getKakao_account().getEmail())
				  .ageRange(profile.getKakao_account().getAge_range())
				  .gender(profile.getKakao_account().getGender())
				  .build();
		
		ArrayList<User> users = new ArrayList<User>();
		users.add(userDB);
		users.add(userBuild);
		
		return users;
	}

	/* 
	 * kakao server에서 승인받은 토큰을 이용해 
	 * 사용자의 kakao profile data를 불러와 
	 * UserEntity에 저장 하는 메소드
	 */
	@Override
	public String SignupAndGetToken(String token, String nickname, String gender, String age, List<MultipartFile> files) {	// token = 액세스토큰
		
		// 카카오 서버에게 사용자 정보 요청
		KakaoProfile profile = findProfile(token);
		
		String fileName = "";
		
		if (files != null) {
			for(MultipartFile file : files) {
				try {
					// s3 file 링크로 fileName 받아와서 postImg data로 저장하면 src로 걍 링크를 긁어오면 화면에 출력됨
					fileName = s3Uploader.uploadFiles(file, "gugu-post");
					System.out.println("s3 file url : " + fileName);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		User user = User.builder()
						  .kakaoId(profile.getId())
						  .kakaoNickname(nickname)
						  .kakaoProfileImg(fileName)
						  .kakaoEmail(profile.getKakao_account().getEmail())
						  .ageRange(age)
						  .gender(gender)
						  .build();
		
		kakaoRepo.save(user);
		
		return createToken(user, token);
	}
//	@Override
//	public ArrayList<String> SaveUserAndGetToken(User user, String token) {	// token = 액세스토큰
//		
//		String userExist = "no";
//		
//		// 카카오 서버에게 사용자 정보 요청
//		KakaoProfile profile = findProfile(token);
//		
//		// 우리 DB에서 카카오아이디로 유저 검색
//		User user = kakaoRepo.findByKakaoId(profile.getId());
//		
//		if(user == null) {		// 첫 로그인 시 회원가입이 안되어 있을 때
//			user = User.builder()
//							  .kakaoId(profile.getId())
//							  .kakaoNickname(profile.getKakao_account().getProfile().getNickname())
//							  .kakaoProfileImg(profile.getKakao_account().getProfile().getProfile_image_url())
//							  .kakaoEmail(profile.getKakao_account().getEmail())
//							  .ageRange(profile.getKakao_account().getAge_range())
//							  .gender(profile.getKakao_account().getGender())
//							  .build();
//			
//			kakaoRepo.save(user);
//		} else {
//			userExist = "yes";
//		}
//		
//		ArrayList<String> returnVal = new ArrayList<String>();
//		returnVal.add(userExist);
//		returnVal.add(createToken(user, token));
//		
//		return returnVal;
//	}
	
	
	/* 
	 * 우리 서버에서 자체적으로 jwt token을 생성해주는 메소드
	 * jwt token에 원하는 데이터를 넣어 줄 수  있다.
	 */
	public String createToken(User user, String token) {
		System.out.println("######## createToken User : " + user);
		System.out.println("######## createToken User email: " + user.getKakaoEmail());
		System.out.println("######## createToken User id: " + user.getKakaoId());
		System.out.println("######## createToken User nickname: " + user.getKakaoNickname());
		System.out.println("######## CREATETOKEN TOKEN : " + token);
		
		String jwtToken = JWT.create()
				
				.withSubject(user.getKakaoEmail())
				.withExpiresAt(new Date(System.currentTimeMillis() + JwtProperties.EXPIRATION_TIME))
				
				.withClaim("id", user.getKakaoId())
				.withClaim("nickname", user.getKakaoNickname())
				.withClaim("access_token", token)
				
				.sign(Algorithm.HMAC512(JwtProperties.SECRET));
		
		return jwtToken;
	}
	
	
	/*
	 * kakao 로그인 시 저장된 user_id(kakaoId)로 회원 정보 불러오는 메소드
	 */
	@Override
	public UserDTO getUser(HttpServletRequest request) {
        Long kakaoId = (Long) request.getAttribute("userCode");
        System.out.println("kakaoId:"+kakaoId);
        User user = kakaoRepo.findByKakaoId(kakaoId);
        UserDTO userDTO = User.entityToDTO(user);
        return userDTO;
	}
	
	@Override
	public User getUserById(Long kakaoId) {
		System.out.println(kakaoId + ": userID(kakaoservice)");
        User user = kakaoRepo.getUserByKakaoId(kakaoId);
        return user;
	}
	
	
	// 로그아웃 메소드 - kakao logout API 호출
	// 해당 액세스 토큰만 만료 처리 - 같은 액세스 토큰을 사용하는 모든 기기에서 로그아웃 됨
//	@Override
//	public ResponseEntity<String> logout(HttpServletRequest request) {
//		
//		// request 안에 들어있는 jwt 토큰의 payload 부분에서 accessToken 정보를 불러옴
//		String token = (String) request.getAttribute("accessToken");
//		String refreshToken = (String) request.getAttribute("refreshToken");
//		
//		// 통신에 필요한 RestTemplate 객체를 만든다
//		RestTemplate rt = new RestTemplate();
//		
//		// http 헤더
//		HttpHeaders headers = new HttpHeaders();
//		headers.add("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
//		headers.add("Authorization", "Bearer " + token); // 헤더에는 발급받은 엑세스 토큰을 넣어 요청해야한다
////		headers.setLocation(URI.create("/"));
//		
//		// HttpHeader 정보를 http 엔터티에 담아준다
//		HttpEntity<MultiValueMap<String, String>> kakaoLogoutRequest =
//				new HttpEntity<>(headers);
//		System.out.println("############ HEADERS : " + headers);
//		
//		// 토큰 정보 확인 - 액세스 토큰이 만료됐으면 401 에러가 뜸
//		ResponseEntity<String> tokenInfoResponse = rt.exchange(
//				"https://kapi.kakao.com/v1/user/access_token_info",
//				HttpMethod.GET,
//				kakaoLogoutRequest,
//				String.class
//		);
//		
//		// Json 응답을 KakaoProfile 객체로 변환해 리턴
//		ObjectMapper objectMapper = new ObjectMapper();
//		TokenInfo tokenInfo = null;
//		try {
//			tokenInfo = objectMapper.readValue(tokenInfoResponse.getBody(), TokenInfo.class);
//		} catch (JsonProcessingException e) {
//			e.printStackTrace();
//		}
//		System.out.println("######### tokenInfo : " + tokenInfo.toString());
//		System.out.println("### status code : " + tokenInfoResponse.getStatusCodeValue());
//		
//		// 토큰이 만료되어 토큰 갱신이 필요할 때
//		if(tokenInfoResponse.getStatusCodeValue() == 401) {
//			// http 바디
//			MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
//			params.add("grant_type", "refresh_token");
//			params.add("client_id", client_id);
//			params.add("refresh_token", refreshToken);
//			
//			// HttpHeader 정보를 http 엔터티에 담아준다
//			HttpEntity<MultiValueMap<String, String>> tokenInfoRequest =
//					new HttpEntity<>(params);
//			
//			// 토큰 갱신
//			ResponseEntity<String> refreshTokenResponse = rt.exchange(
//					"https://kapi.kakao.com/oauth/token",
//					HttpMethod.POST,
//					tokenInfoRequest,
//					String.class
//			);
//			
//			// Json 응답을 KakaoProfile 객체로 변환해 리턴
//			ObjectMapper objectMapper2 = new ObjectMapper();
//			RefreshTokenInfo refreshTokenInfo = null;
//			try {
//				refreshTokenInfo = objectMapper2.readValue(refreshTokenResponse.getBody(), RefreshTokenInfo.class);
//			} catch (JsonProcessingException e) {
//				e.printStackTrace();
//			}
//			System.out.println("######### refreshTokenInfo : " + refreshTokenInfo.toString());
//			
//			HttpHeaders headers2 = new HttpHeaders();
//			headers2.add("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
//			headers2.add("Authorization", "Bearer " + token); // 헤더에는 발급받은 엑세스 토큰을 넣어 요청해야한다
//			
//			// HttpHeader 정보를 http 엔터티에 담아준다
//			HttpEntity<MultiValueMap<String, String>> kakaoLogoutRequest2 =
//					new HttpEntity<>(headers2);
//			
//			ResponseEntity<String> kakaoLogoutResponse = rt.exchange(
////					"https://kapi.kakao.com/v1/user/unlink",		// 동의 전으로 돌아가기
//					"https://kapi.kakao.com/v1/user/logout",		// 그냥 로그아웃하기 
//					HttpMethod.POST,
//					kakaoLogoutRequest2,
//					String.class
//			);
//			
//			return kakaoLogoutResponse;
//		}
//		
//		
//		/*
//		 * - HttP 요청 (POST 방식) 후, response 변수에 응답을 받음
//		 * - 해당 주소로 Http 요청을 보내 String 변수에 응답을 받는다
//		 * - access token 방식으로 request를 보내 logout을 실행하는 API 호출 
//		 */
//		ResponseEntity<String> kakaoLogoutResponse = rt.exchange(
////				"https://kapi.kakao.com/v1/user/unlink",		// 동의 전으로 돌아가기
//				"https://kapi.kakao.com/v1/user/logout",		// 그냥 로그아웃하기 
//				HttpMethod.POST,
//				kakaoLogoutRequest,
//				String.class
//		);
//		System.out.println("############ kakaoLogoutResponse : " + kakaoLogoutResponse);
//		
//		return kakaoLogoutResponse;
//	}
	
	// 로그아웃 할 때 액세스코드가 아닌 해당 카카오ID로 로그인 된 모든 기기에서 로그아웃하기
	// 해당 사용자의 모든 토큰 만료 처리(브라우저 달라도 로그아웃 되지 않을까!?)
	@Override
	public ResponseEntity<String> logout2(HttpServletRequest request) {
		// request 안에 들어있는 jwt 토큰의 payload 부분에서 kakaoId 정보를 불러옴
		String kakaoId = request.getAttribute("userCode").toString();
		System.out.println("### kakaoId : " + kakaoId);
		
		// 통신에 필요한 RestTemplate 객체를 만든다
		RestTemplate rt = new RestTemplate();
		
		// http 헤더
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "KakaoAK " + adminKey);
		
		// http 바디
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("target_id_type", "user_id");
		params.add("target_id", kakaoId);
		
		// HttpHeader 정보를 http 엔터티에 담아준다
		HttpEntity<MultiValueMap<String, String>> logoutRequest =
				new HttpEntity<>(params, headers);
		
		// 토큰 갱신
		ResponseEntity<String> kakaoLogoutResponse = rt.exchange(
				"https://kapi.kakao.com/v1/user/logout",
				HttpMethod.POST,
				logoutRequest,
				String.class
		);
		
		System.out.println("###### logout2 : " + kakaoLogoutResponse);
		
		return kakaoLogoutResponse;
	}


	@Override
	@Transactional
	public void userUpdate(Long userId, String email, String nickname, String fileName) {
		User user = kakaoRepo.findByKakaoId(userId);
		
		if(fileName=="") {
			user.updateUser(email, nickname);
		} else {
			user.updateUser(email, nickname, fileName);
		}
		
		System.out.println("변경완료!!" + user.getKakaoNickname());
		
	}
}
