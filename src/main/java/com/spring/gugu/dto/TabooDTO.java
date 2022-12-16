package com.spring.gugu.dto;

import java.sql.Timestamp;

import com.spring.gugu.entity.Taboo;
import com.spring.gugu.entity.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class TabooDTO {
	private long tabooNo;
	private long roomNo;
	private String tabooWord;
	private Timestamp createAt;
	
	// 생성자 함수
	public TabooDTO(long roomNo, String tabooWord) {
		this.roomNo = roomNo;
		this.tabooWord = tabooWord;
	}
	
	public static Taboo dtoToEntity(TabooDTO tabooDTO) {
		Taboo taboo = Taboo.builder()
							.roomNo(tabooDTO.getRoomNo())
							.tabooWord(tabooDTO.getTabooWord())
							.build();
		return taboo;
	}
}
