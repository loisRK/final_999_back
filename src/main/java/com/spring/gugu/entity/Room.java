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

import com.spring.gugu.dto.RoomDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"user"})
@Getter
@Builder
@Table(name = "room")
public class Room {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "room_no")
	private Long roomNo;
	
	// user table - user_id FK
	// fetchtype.EAGER : 즉시 로딩, 코드 실행 시 참조되는 쿼리도 즉시 실행하는 것
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	@NotFound(action = NotFoundAction.IGNORE) // 값이 발견되지 않으면 무시
	private User user;
	
	@Column(name = "chat_lat")
	private double chatLat;
	
	@Column(name = "chat_long")
	private double chatLong;
	
	@Column(name = "user_cnt")
	private long userCnt;
	
	private String category;
	
	@Column(name = "created_at")
	@CreationTimestamp
	private Timestamp createdAt;
	
	@Column(name = "room_title")
	private String title;
	
	public static RoomDTO entityToDTO(Room room) {
		RoomDTO roomDTO = RoomDTO.builder()
								.roomNo(room.getRoomNo())
								.user(room.getUser())
								.chatLat(room.getChatLat())
								.chatLong(room.getChatLong())
								.userCnt(room.getUserCnt())
								.category(room.getCategory())
								.createdAt(room.getCreatedAt())
								.title(room.getTitle())
								.build();
		return roomDTO;
						
																
	}
	
	// 새로운 비둘기 입장 
	public void addClient() {
		this.userCnt = this.userCnt + 1;
	}
	
	// 배부른 비둘기 퇴장 ( 날아가기 )
	public void exitClient() {
		this.userCnt = this.userCnt - 1;
	}

}
