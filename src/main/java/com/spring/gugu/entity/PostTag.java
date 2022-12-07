package com.spring.gugu.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Builder
@Table(name = "post_tag")
@IdClass(PostTag.class)	// 복합키 설정을 위한 annotation
public class PostTag implements Serializable {

	// tag_lib table의 tag_no FK
	@Id
	@Column(name = "tag_no")
	private long tagNo;
	
	// post table의 post_no FK
	@Id
	@Column(name = "post_no")
	private long postNo;
}
