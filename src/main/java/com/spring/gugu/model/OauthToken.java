package com.spring.gugu.model;

import lombok.Data;

@Data
public class OauthToken {	// 되도록 들어오는 json 객체를 콘솔에 한번 찍어보고 정확히 만드는 것을 추천한다.
	private String access_token;
	private String token_type;
	private String refresh_token;
	private String id_token;
	private int expires_in;
	private String scope;
	private int refresh_token_expires_in;
}
