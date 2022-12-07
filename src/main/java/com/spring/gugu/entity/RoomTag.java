package com.spring.gugu.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

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
@Table(name = "room_tag")
@IdClass(RoomTag.class)	// 복합키 설정을 위한 annotation
public class RoomTag {

	// tag_lib table의 tag_no FK
	@Id
	@Column(name = "tag_no")
	private long tagNo;
	
	// room table의 room_no FK
	@Id
	@Column(name = "room_no")
	private long roomNo;
}
