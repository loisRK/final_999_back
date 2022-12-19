package com.spring.gugu.model;

import lombok.Data;

@Data
public class TokenInfo {
	public int expiresInMillis;
	public Long id;
	public int expires_in;
	public int app_id;
	public int appId;
}
