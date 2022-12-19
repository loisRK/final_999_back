package com.spring.gugu.model;

import lombok.Data;

@Data
public class RefreshTokenInfo {
	public String token_type;
	public String access_token;
	public int expires_in;
}
