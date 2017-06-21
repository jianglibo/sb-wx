package com.jianglibo.wx.domain;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "postshare", uniqueConstraints = { @UniqueConstraint(columnNames = {"post_id", "user_id"})})
public class PostShare extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@NotNull
	@ManyToOne
	@JoinColumn(name="post_id")
	private Post post;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name="user_id")
	private BootUser bootUser;
	
	public PostShare() {
	}
	
	public PostShare(Post post, BootUser user) {
		this.post = post;
		this.bootUser = user;
	}


	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

	public BootUser getBootUser() {
		return bootUser;
	}

	public void setBootUser(BootUser bootUser) {
		this.bootUser = bootUser;
	}
	
}
