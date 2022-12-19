package com.spring.gugu.controller;

import java.util.List;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spring.gugu.dto.ReportDTO;
import com.spring.gugu.dto.RoomDTO;
import com.spring.gugu.entity.User;
import com.spring.gugu.service.ReportServiceImpl;
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
	
	@Autowired
	ReportServiceImpl reportService;
	
	//채팅방 생성
	@PostMapping("/room")
	public Long insertRoom(@RequestParam("userId") User user, @RequestParam("category") String category, @RequestParam("chatLat") double chatLat, @RequestParam("chatLong") double chatLong, @RequestParam("tag") String tag) {
		
		RoomDTO roomDTO = RoomDTO.builder()
								.user(user)
								.category(category)
								.chatLat(chatLat)
								.chatLong(chatLong)
								.title(tag)
								.build();
		
		System.out.println("roomDTO" + roomDTO);
		return roomService.insertRoom(roomDTO);				
		
	}
	
	// 채팅방 리스트 모두 출력하기.
	@GetMapping("/roomList")
	public List<RoomDTO> getAllRooms() {
		List<RoomDTO> allRooms = roomService.findall();
		System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$" + allRooms);
		return allRooms;
	}
	
	// 채팅방 (room)정보 전부 가져오기.
	@GetMapping("/room/{roomNo}")
	public RoomDTO roomInfo(@PathVariable Long roomNo) {
		RoomDTO roomDTO = null;
		roomDTO = roomService.roomInfo(roomNo);
		return roomDTO;
	}
	
	
	// 채팅방 접속자수  ++
	@GetMapping("/clientIn/{roomNo}")
	public void clientIn(@PathVariable Long roomNo) {
		roomService.clientIn(roomNo);
	}
	// 채팅방 접속자수  --
	@GetMapping("/clientOut/{roomNo}")
	public void clientOut(@PathVariable Long roomNo) {
		roomService.clientOut(roomNo);
	}
	
	
	// 맘에들지 않는 둘기 신고하기
	@PostMapping("/report")
	public int insertReport(@RequestParam("roomNo") long roomNo, @RequestParam("message") String message, @RequestParam("reporterId") long reporterId, @RequestParam("reportedId") long reportedId) {
		
		System.out.println("######## /report : " + roomNo + " " + message + " " + reporterId + " " + reportedId);
		// DTO 객체 생성.
		ReportDTO reportDTO = ReportDTO.builder()
				 					   .roomNo(roomNo)
				 					   .message(message)
				 					   .reporterId(reporterId)
				 					   .reportedId(reportedId)
				 					   .build();
		
		//잘 가져와 지는 것을 확인 !
		System.out.println("reportDTO : " + reportDTO);
		
		int reportNum = reportService.insertReport(reportDTO);
		System.out.println("###### reportNum : " + reportNum);
		
		return reportNum;
	}


	
	@GetMapping("/deleteRoom/{roomNo}")
	public void deleteRoom(@PathVariable Long roomNo) {
		
		roomService.deleteRoom(roomNo);
	}
	

//	@GetMapping("/reportNum")
//	public int insertReport(@RequestParam("roomNo") Long roomNo, @RequestParam("reportedId") Long reportedId) {
//		System.out.println("############# roomNo and reportedId : " + roomNo + " " + reportedId);
//		int reportNum = reportService.getReportNum(roomNo, reportedId);
//		
//		System.out.println("###### reportNum : " + reportNum);
//		
//		return reportNum;
//	}

}
