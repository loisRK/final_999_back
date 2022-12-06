package com.spring.diary.service;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.diary.entity.ChatEntity;
import com.spring.diary.repository.ChatRepository;

@Service
public class ChatServiceImpl implements ChatService {
	
	@Autowired
	ChatRepository chatRepo;

	@Override
	public List<ChatEntity> getChattings() throws ExecutionException, InterruptedException {
		// TODO Auto-generated method stub
		return chatRepo.getChattings();
	}

}
