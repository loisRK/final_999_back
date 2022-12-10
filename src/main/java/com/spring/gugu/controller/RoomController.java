package com.spring.gugu.controller;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spring.gugu.dto.RoomDTO;
import com.spring.gugu.entity.User;
import com.spring.gugu.service.RoomServiceImpl;

@RestController		// 페이지 전환이 필요없으므로 restController 사용
@RequestMapping(value = "/api")
@CrossOrigin(origins = {"http://localhost:3000"})
@DynamicUpdate
//@RequiredArgsConstructor
public class RoomController {
	
	// 객체 생성
	@Autowired
	RoomServiceImpl roomService;
	
	//채팅방 생성
	@PostMapping("/room")
	public void insertRoom(@RequestParam("userId") User user, @RequestParam("category") String category, @RequestParam("chatLat") double chatLat, @RequestParam("chatLong") double chatLong) {
		
		RoomDTO roomDTO = RoomDTO.builder()
								.user(user)
								.category(category)
								.chatLat(chatLat)
								.chatLong(chatLong)
								.build();
		
		System.out.println("roomDTO" + roomDTO);
		roomService.insertRoom(roomDTO);				
		
	}
	
	// 채팅방 (room)정보 전부 가져오기.
	@GetMapping("/room/{roomNo}")
	public RoomDTO roomInfo(@PathVariable Long roomNo) {
		RoomDTO roomDTO = null;
		roomDTO = roomService.roomInfo(roomNo);
		return roomDTO;
	}
	
	

}
