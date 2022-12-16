package com.spring.gugu.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import com.spring.gugu.dto.TabooDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Builder
@Table(name = "taboo")
public class Taboo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "taboo_no")
	private long tabooNo;
	
	// room table의 room_no FK
	@Column(name = "room_no")
	private long roomNo;
	
	@Column(name = "taboo_word")
	private String tabooWord;
	
	@Column(name = "create_at")
	@CreationTimestamp
	private Timestamp createAt;
	
	
	// Entity -> DTO
	public static TabooDTO entityToDTO(Taboo taboo) {
		TabooDTO tabooDTO = TabooDTO.builder()
									.tabooNo(taboo.getTabooNo())
									.roomNo(taboo.getRoomNo())
									.tabooWord(taboo.getTabooWord())
									.createAt(taboo.getCreateAt())
									.build();
		
		return tabooDTO;
	}
}
