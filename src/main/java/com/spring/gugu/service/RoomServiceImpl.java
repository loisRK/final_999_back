package com.spring.gugu.service;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.gugu.dto.RoomDTO;
import com.spring.gugu.entity.Room;
import com.spring.gugu.repository.RoomRepository;

@Service
public class RoomServiceImpl implements RoomService{
	
	@Autowired
	RoomRepository roomRepo;

	@Override
	public void insertRoom(RoomDTO roomDTO) {
		Room room = RoomDTO.dtoToEntity(roomDTO);
		roomRepo.save(room);
	}

	public RoomDTO roomInfo(Long roomNo) {
		
		Room room = roomRepo.getById(roomNo);
		RoomDTO roomDTO = room.entityToDTO(room);
		return roomDTO;
	}

}
