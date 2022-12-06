package com.spring.diary.service;

import java.util.List;
import java.util.concurrent.ExecutionException;

import com.spring.diary.entity.ChatEntity;

public interface ChatService {

	List<ChatEntity> getChattings() throws ExecutionException, InterruptedException;
}
