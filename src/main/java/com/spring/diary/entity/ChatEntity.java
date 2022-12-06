package com.spring.diary.entity;

import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class ChatEntity {
	private Long id;
	private String name;
	private String content;
	private Date create_dt;

}
