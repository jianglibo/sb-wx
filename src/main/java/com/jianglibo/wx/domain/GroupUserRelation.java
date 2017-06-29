package com.jianglibo.wx.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "groupuser", uniqueConstraints = { @UniqueConstraint(columnNames = {"group_id", "user_id"})})
public class GroupUserRelation extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5610224553913163277L;

	@NotNull
	@ManyToOne(fetch= FetchType.EAGER)
	@JoinColumn(name="group_id")
	private BootGroup bootGroup;
	
	@NotNull
	@JoinColumn(name="user_id")
	@ManyToOne(fetch= FetchType.EAGER)
	private BootUser bootUser;
	
	public GroupUserRelation() {
	}
	
	public GroupUserRelation(BootGroup group, BootUser user) {
		this.bootGroup = group;
		this.bootUser = user;
	}

	public BootGroup getBootGroup() {
		return bootGroup;
	}

	public void setBootGroup(BootGroup bootGroup) {
		this.bootGroup = bootGroup;
	}

	public BootUser getBootUser() {
		return bootUser;
	}

	public void setBootUser(BootUser bootUser) {
		this.bootUser = bootUser;
	}

	@Override
	public String[] propertiesOnCreating() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
