package com.spring.diary.controller;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.diary.entity.ChatEntity;
import com.spring.diary.service.ChatServiceImpl;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/exam/svc/v1")
public class ChatController {
	
	private final ChatServiceImpl chatService;
	
	@GetMapping("/chatting")
    public ResponseEntity<Object>  getUsers() throws ExecutionException, InterruptedException {
        List<ChatEntity> list = chatService.getChattings();
        return ResponseEntity.ok().body(list);

    }
	
}
