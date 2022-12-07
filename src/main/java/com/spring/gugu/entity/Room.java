package com.spring.gugu.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

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
@Table(name = "room")
public class Room {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "room_no")
	private long roomNo;
	
	// user table - user_id FK
	// fetchtype.EAGER : 즉시 로딩, 코드 실행 시 참조되는 쿼리도 즉시 실행하는 것
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	@NotFound(action = NotFoundAction.IGNORE) // 값이 발견되지 않으면 무시
	private User roomUser;
	
	@Column(name = "chat_lat")
	private int chatLat;
	
	@Column(name = "chat_long")
	private int chatLong;
	
	@Column(name = "user_cnt")
	private long userCnt;
	
	private String category;
	
	@Column(name = "created_at")
	@CreationTimestamp
	private Timestamp createdAt;
	

}
