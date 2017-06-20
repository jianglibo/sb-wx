package com.jianglibo.wx.domain;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

@Table(name = "followrelation", uniqueConstraints = { @UniqueConstraint(columnNames = {"group_id", "user_id"})})
public class GroupUserRelation extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5610224553913163277L;

	@NotNull
	@ManyToOne
	@JoinColumn(name="group_id")
	private BootGroup bootGroup;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name="user_id")
	private BootUser bootUser;

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
	
	
}
