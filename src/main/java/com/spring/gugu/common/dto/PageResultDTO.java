package com.spring.gugu.common.dto;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.data.domain.Page;

import lombok.Data;

// <DTO, EN> - DTO, Entity에 대한 어떤 타입이 와도 상관없이 쓸 수 있게 하는 제네릭 표현
@Data
public class PageResultDTO<DTO, EN> {

	// DTO 리스트
	private List<DTO> dtoList;
	
	// 총 페이지 번호
	private int totalPage;
	
	// 현재 페이지 번호
	private int page;
	
	// 페이지 목록 개수
	private int size;
	
	// 시작 페이지, 종료 페이지
	private int start, end;
	
	// 이전, 다음 페이지
	private boolean prev, next;
	
	// 페이지 출력 개수
	private List<Integer> pageList;
	
	// 페이지 값 초기화를 위해 resultDTO 객체를 받아와야 함
	public PageResultDTO(Page<EN> result, Function <EN, DTO> fn) {
		// 함수 타입으로 선언해주어야 함 
		this.dtoList = result.stream()
//							.map(diary -> Diary.entityToDTO(diary))
							.map(fn)
							.collect(Collectors.toList());
		
		this.totalPage = result.getTotalPages();
		
		// 화면상에서 1 페이지 부터 시작하게 하기 위함
		this.page = result.getPageable().getPageNumber() + 1;
		this.size = result.getPageable().getPageSize();
		
		int tempEnd = (int)(Math.ceil(page/10.0)) * 10;
		
		this.start = tempEnd - 9;
		this.prev = start > 1;
		this.end = totalPage > tempEnd? tempEnd : totalPage;
		this.next = totalPage > tempEnd;
		
		this.pageList = IntStream.rangeClosed(this.start, this.end)
								.boxed()
								.collect(Collectors.toList());
		
	}
	
	
	
	

}
