package com.spring.gugu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing		// 등록 시간 자동 업데이트를 위한 annotation
public class Step15DiaryApplication {

	public static void main(String[] args) {
		SpringApplication.run(Step15DiaryApplication.class, args);
	}

}
