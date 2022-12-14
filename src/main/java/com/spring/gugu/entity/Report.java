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
@Table(name = "report")
public class Report {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "report_no")
	private long reportNo;
	
	// room table의 room_no FK
	@Column(name = "room_no")
	private long roomNo;
	
	@Column(name = "reported_at")
	@CreationTimestamp
	private Timestamp reportedAt;
	
	private String message;
	
	// user_id 값이지만 채팅 서버에서 객체로 전달받는 값으로 입력할 것이므로 상속관계가 없음
	@Column(name = "reporter_id")
	private long reporterId;
	
	// user_id 값이지만 채팅 서버에서 객체로 전달받는 값으로 입력할 것이므로 상속관계가 없음
	@Column(name = "reported_id")
	private long reportedId;
}
