package com.spring.gugu.entity;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LikeId implements Serializable{
	// 복합키 설정을 위한 복합키 클래스 생성
	private int postNo;
	private int userId;
}
