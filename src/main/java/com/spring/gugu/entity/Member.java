package com.spring.gugu.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "member")
public class Member {

	// user table의 user_id FK
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long userId;
	
	// room table의 room_no FK
	@Column(name = "room_no", nullable = false)
	private long roomNo;
	
	@Column(name = "roomIn_date", nullable = false)
	private Timestamp roomInDate;
	
	@Column(name = "roomOut_date", nullable = false)
	private Timestamp roomOutDate;
}
