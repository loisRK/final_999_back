package com.spring.gugu.entity;

import java.io.Serializable;
import java.sql.Timestamp;

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
@Table(name = "like")
@IdClass(LikeId.class)	// 복합키 설정을 위한 annotation
public class Like implements Serializable {

	@Id
	@Column(name = "post_no")
	private long postNo;
	
	@Id
	@Column(name = "user_id")
	private long userId;
	
	@Column(name = "after_like")
	private int afterLike;
	
}
