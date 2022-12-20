package com.spring.gugu.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.gugu.dto.PostDTO;
import com.spring.gugu.dto.RoomDTO;
import com.spring.gugu.entity.Post;
import com.spring.gugu.entity.Room;
import com.spring.gugu.repository.RoomRepository;

@Service
public class RoomServiceImpl implements RoomService{
	
	@Autowired
	RoomRepository roomRepo;

	@Override
	public Long insertRoom(RoomDTO roomDTO) {
		Room room = RoomDTO.dtoToEntity(roomDTO);
		return roomRepo.save(room).getRoomNo();
	}
	
	public RoomDTO roomInfo(Long roomNo) {
		
		Room room = roomRepo.getById(roomNo);
		RoomDTO roomDTO = Room.entityToDTO(room);
		return roomDTO;
	}

	// 룸 넘버 찾기 !!!
	public RoomDTO roomNoInfo(String user_id) {
		
		Room room = roomRepo.findByUser(user_id);
		RoomDTO roomDTO = Room.entityToDTO(room);
		return roomDTO;
	}

	public List<RoomDTO> findall() {
		List<Room> allRooms = roomRepo.findAll();
		
	      // Room 타입을 RoomDTO로 타입으로 변경
	      Function<Room, RoomDTO> fn = (room -> Room.entityToDTO(room));
	      List<RoomDTO> allRoomDTOs = allRooms.stream()
	                                 .map(fn)
	                                 .collect(Collectors.toList());
	      
	      return allRoomDTOs;
	}

	// 채팅방에 새로운 client count ++
	public void clientIn(Long roomNo) {
		Room room = roomRepo.getById(roomNo);
		room.addClient();
		roomRepo.save(room);
	}

	// 채팅방에 새로운 client count --
	public void clientOut(Long roomNo) {
		Room room = roomRepo.getById(roomNo);
		room.exitClient();
		roomRepo.save(room);
	}

	@Transactional
	public void deleteRoom(Long roomNo) {
		roomRepo.deleteById(roomNo);
	}

}
