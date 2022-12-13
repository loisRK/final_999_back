package com.spring.gugu.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
@Table(name = "tag_lib")
public class TagLibrary {

	@Id
	@Column(name = "tag_no")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long tagNo;
	
	@Column(name = "tag_name")
	private String tagName;
}
