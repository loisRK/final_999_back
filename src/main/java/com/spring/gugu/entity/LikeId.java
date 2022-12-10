//package com.spring.gugu.entity;
//
//import java.io.Serializable;
//
//import javax.persistence.Embeddable;
//import javax.persistence.JoinColumn;
//import javax.persistence.ManyToOne;
//import javax.persistence.OneToOne;
//
//import lombok.Builder;
//import lombok.Data;
//
//@Data
//@Embeddable
//public class LikeId implements Serializable {
//	
//	@OneToOne
//	@JoinColumn(name = "user_id")
//	private User user;
//	
//	@ManyToOne
//	@JoinColumn(name = "post_no")
//	private Post post;
//	
//	
//	public void likeId(User user, Post post) {
//		this.user = user;
//		this.post = post;
//	}
//
//}
