package com.spring.gugu.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

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
	@Column(name = "user_id", nullable = false)
	private long userId;
	
	@Column(name = "chat_lat")
	private int chatLat;
	
	@Column(name = "chat_long")
	private int chatLong;
	
	@Column(name = "user_cnt")
	private long userCnt;
	
	@Column(name = "category")
	private String category;
	
	@Column(name = "created_at")
	@CreationTimestamp
	private Timestamp createdAt;
	

}
