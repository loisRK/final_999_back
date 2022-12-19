package com.spring.gugu.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spring.gugu.config.jwt.JwtProperties;
import com.spring.gugu.dto.UserDTO;
import com.spring.gugu.entity.User;
import com.spring.gugu.model.OauthToken;
import com.spring.gugu.service.KakaoServiceImpl;

@RestController		// 메소드 리턴타입 객체를 json으로 자동 파싱 해준다. 이동이 아니라 값만 받는 용
@RequestMapping(value = "/api")
@CrossOrigin(origins = {"*"})
public class KakaoController {
	
	@Autowired
	private KakaoServiceImpl kakaoService;
	
	// 프론트에서 인가코드 받아오는 URL
	@GetMapping("/oauth/token")
	public ResponseEntity<String> getLogin(@RequestParam("code") String code) {
//		System.out.println("############CODE " + code);
		
		// 넘겨온 인가코드를 통해 access_token 발급
		OauthToken oauthToken = kakaoService.getAccessToken(code);
		System.out.println("################################ oauthToken : " + oauthToken.toString());
		
		// 발급 받은 accessToken으로 카카오 회원 정보를 DB에 저장 후 JWT를 생성
		String jwtToken = kakaoService.SaveUserAndGetToken(oauthToken.getAccess_token());
//		System.out.println("################################ jwtToken : " + jwtToken.toString());
		
		// 응답 헤더의 Authorization 이라는 항목에 JWT 를 넣어준다
		HttpHeaders headers = new HttpHeaders();
		headers.add(JwtProperties.HEADER_STRING, JwtProperties.TOKEN_PREFIX + jwtToken);
//		System.out.println("################################ headers : " + headers.toString());
		
		// JWT 가 담긴 헤더와 200 ok 스테이터스 값, "success" 라는 바디값을 ResponseEntity 에 담아 프론트 측에 전달한다
		System.out.println("##### " + ResponseEntity.ok().headers(headers).body("success"));
		return ResponseEntity.ok().headers(headers).body("success");
	}
	
    // jwt 토큰으로 유저정보 요청하기
//    @GetMapping("/myPage")
//    public ResponseEntity<Object> getCurrentUser(HttpServletRequest request) {
//    	Long kakaoId = (Long) request.getAttribute("userCode");    	
//    	System.out.println("kakaoID : " + kakaoId);
//        User user = kakaoService.getUserById(kakaoId);
//        System.out.println("##### USER " + user);
//
//        return ResponseEntity.ok().body(user);
//    }
    
    @GetMapping("/myPage")
    public ResponseEntity<Object> getCurrentUser(HttpServletRequest request) {
//    	System.out.println("########## REQUEST " + request.getHeader("request"));
    	
        UserDTO userDTO = kakaoService.getUser(request);
//        System.out.println("##### USER " + user);

        return ResponseEntity.ok().body(userDTO);
    }
    
    
    @GetMapping("/kakaoLogout")
    public ResponseEntity<String> getLogout(HttpServletRequest request) {
		
    	ResponseEntity<String> response = kakaoService.logout2(request);
//    	ResponseEntity<String> response = kakaoService.logout(request);
    	
    	System.out.println("######### RESPONSE : " + response);
    	
		return ResponseEntity.ok().body("success");
    }
}
