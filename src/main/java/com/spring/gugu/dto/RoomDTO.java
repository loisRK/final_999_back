package com.spring.gugu.dto;

import java.sql.Timestamp;

import com.spring.gugu.entity.Room;
import com.spring.gugu.entity.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RoomDTO {
	private Long roomNo;
	private User user;
	private double chatLat;
	private double chatLong;
	private long userCnt;
	private String category;
	private Timestamp createdAt;
	
	public RoomDTO(User user, String category, double chatLat, double chatLong) {
		this.user = user;
		this.category = category;
		this.chatLat = chatLat;
		this.chatLong = chatLong;
	}
	
	public static Room dtoToEntity(RoomDTO roomDTO) {
		Room room = Room.builder()
				 		.user(roomDTO.getUser())
				 		.category(roomDTO.getCategory())
				 		.chatLat(roomDTO.getChatLat())
				 		.chatLong(roomDTO.getChatLong())
				 		.build();
		return room;
	}

}
