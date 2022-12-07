package com.spring.gugu.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Getter
@Builder
@Table(name = "like")
//@IdClass(LikeId.class)	// 복합키 설정을 위한 annotation
public class Like implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "like_id")
	private Long likeId;
	
	@Column(name = "after_like")
	private int afterLike;
	
}
