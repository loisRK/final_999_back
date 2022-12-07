package com.spring.gugu.common.exception;

import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// 컨트롤러에 발생하는 모든 에러처리 공통으로 처리하는 메소드
@RestControllerAdvice        // AOP 관점지향으로 존재한다.  > 지켜보다 에러발생하면 함수 실행.
public class ApiException {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
   
	@ExceptionHandler(NoSuchElementException.class)
	public ResponseEntity<String> handlerNoSuchElementException(NoSuchElementException e){
		
//         return ResponseEntity
//        		 			.status(ExceptionEnum.NOT_FOUND.getStatusCode())
//        		 			.body(ExceptionEnum.NOT_FOUND.getMsg());
        return ResponseEntity
				.status(ExceptionEnum.E0001.getStatus())
				.body(ExceptionEnum.E0001.getMsg());
//         return ResponseEntity.status(HttpStatus.NOT_FOUND).body(" 해당 요청값은 존재하지 않습니다! ");
     }
}