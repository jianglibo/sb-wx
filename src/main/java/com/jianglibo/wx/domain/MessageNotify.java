package com.jianglibo.wx.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "messagenotify", uniqueConstraints = { @UniqueConstraint(columnNames = {"ntype", "user_id"}) })
public class MessageNotify extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 493332625980265824L;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="user_id")
	private BootUser bootUser;
	
	private String ntype;
	
	private int number;

	public BootUser getBootUser() {
		return bootUser;
	}

	public void setBootUser(BootUser bootUser) {
		this.bootUser = bootUser;
	}

	public String getNtype() {
		return ntype;
	}

	public void setNtype(String ntype) {
		this.ntype = ntype;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	@Override
	public String[] propertiesOnCreating() {
		// TODO Auto-generated method stub
		return null;
	}
}
