package com.jianglibo.wx.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "unread", uniqueConstraints = { @UniqueConstraint(columnNames = {"obid","type", "user_id"})})
public class Unread extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private long obid;
	
	private String type;
	
//	private boolean read;
	
	@NotNull
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="user_id")
	private BootUser bootUser;
	
	
	public BootUser getBootUser() {
		return bootUser;
	}

	public void setBootUser(BootUser bootUser) {
		this.bootUser = bootUser;
	}

	public long getObid() {
		return obid;
	}

	public void setObid(long obid) {
		this.obid = obid;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String[] propertiesOnCreating() {
		return null;
	}

//	public boolean isRead() {
//		return read;
//	}
//
//	public void setRead(boolean read) {
//		this.read = read;
//	}

}
