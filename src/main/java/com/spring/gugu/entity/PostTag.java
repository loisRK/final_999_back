//package com.spring.gugu.entity;
//
//import javax.persistence.CascadeType;
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.FetchType;
//import javax.persistence.Id;
//import javax.persistence.IdClass;
//import javax.persistence.JoinColumn;
//import javax.persistence.ManyToOne;
//
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//
//@Getter
//@NoArgsConstructor
//@Entity
//@IdClass(PostTagId.class)
//public class PostTag {
//	
//	@Id
//	@ManyToOne(fetch = FetchType.LAZY)
//	@Column(name = "tag_no")
//	private Long tagNo;
//	
//	@Id
//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "postNo")
//	private Long postNo;
//
//}
