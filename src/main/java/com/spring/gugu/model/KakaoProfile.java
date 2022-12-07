package com.spring.gugu.model;

import lombok.Data;

@Data
public class KakaoProfile {
	public Long id;
	public String connected_at;
	public Properties properties;
	public KakaoAccount kakao_account;
	
	// 카카오 프로필 이미지를 가져올 경우 Properties 클래스에서 가져오지 않는게 좋다!!
	// Properties의 이미지는 null 이지만 KakaoAccount의 이미지는 null 이 아닌 경우가 있었다
	@Data
	public class Properties { 
		public String nickname;
		public String profile_image;
		public String thumbnail_image;
	}
	
	@Data
	public class KakaoAccount {
		public Boolean profile_nickname_needs_agreement;
		public Boolean profile_image_needs_agreement;
		public Profile profile;
		public Boolean has_email;
		public Boolean email_needs_agreement;
		public Boolean is_email_valid;
		public Boolean is_email_verified;
		public String email;
		public Boolean has_age_range;
		public Boolean age_range_needs_agreement;
		public String age_range;
		public Boolean has_gender;
		public Boolean gender_needs_agreement;
		public String gender;
		
		@Data
		public class Profile {
			public String nickname;
			public String thumbnail_image_url;
			public String profile_image_url;
			public Boolean is_default_image;
		}
	}
}
