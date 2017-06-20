package com.jianglibo.wx.domain;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "followrelation", uniqueConstraints = { @UniqueConstraint(columnNames = {"follower_id", "befollowed_id"})})
public class FollowRelation extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1596298446874055251L;
	
	public FollowRelation() {
	}
	
	public FollowRelation(BootUser follower, BootUser befollowed) {
		this.follower = follower;
		this.befollowed = befollowed;
	}
	
	
	@NotNull
	@ManyToOne
	@JoinColumn(name="follower_id")
	private BootUser follower;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "befollowed_id")
	private BootUser befollowed;
	

	public BootUser getFollower() {
		return follower;
	}
	public void setFollower(BootUser follower) {
		this.follower = follower;
	}
	public BootUser getBefollowed() {
		return befollowed;
	}
	public void setBefollowed(BootUser befollowed) {
		this.befollowed = befollowed;
	}
	
	

}
