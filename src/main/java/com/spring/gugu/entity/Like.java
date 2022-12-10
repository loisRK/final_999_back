//package com.spring.gugu.entity;
//
//import java.time.LocalDateTime;
//
//import javax.persistence.Column;
//import javax.persistence.EmbeddedId;
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.JoinColumn;
//import javax.persistence.ManyToOne;
//import javax.persistence.OneToOne;
//import javax.persistence.Table;
//
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.ToString;
//
//@Entity
//@AllArgsConstructor
//@NoArgsConstructor
//@ToString
//@Getter
//@Builder
//@Table(name = "like")
//public class Like {
//	
////	@Id
////	@GeneratedValue(strategy = GenerationType.IDENTITY)
////	@Column(name = "like_no")
////	private Long likeNo;
//	
//	@EmbeddedId
//	private LikeId id;
//	
//	@Column(name = "after_like")
//	private int afterLike;
//
//	public static Like likeId(User user, Post post) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//}
