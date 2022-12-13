package com.spring.gugu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.gugu.entity.Room;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
	
	Room findByUser(String user_id);
}
