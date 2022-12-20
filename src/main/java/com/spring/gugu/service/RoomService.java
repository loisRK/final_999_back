package com.spring.gugu.service;

import java.util.List;

import com.spring.gugu.dto.RoomDTO;
import com.spring.gugu.entity.Room;

public interface RoomService {
	
	public Long insertRoom(RoomDTO roomDTO);
	
	public RoomDTO roomInfo(Long roomNo);
	
	public RoomDTO roomNoInfo(String user_id);
	
	public List<RoomDTO> findall();
	
	public void clientIn(Long roomNo);

	public void clientOut(Long roomNo);
	
	public void deleteRoom(Long roomNo);

}
