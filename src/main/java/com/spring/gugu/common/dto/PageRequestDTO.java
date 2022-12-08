// 공통으로 사용하게 되는 dto이므로 common.dto package생성
package com.spring.gugu.common.dto;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class PageRequestDTO {
	// Pageable 객체 반환을 위한 DTO
	// 화면에서 전달되는 page, size
	private int page;
	private int size;
	
	public PageRequestDTO() {
		this.page = 1;
		this.size = 10;
	}
	
	public Pageable getPageable() {
		return PageRequest.of(page-1, size, Sort.by("postNo").descending());
	}
	
	
	
}
