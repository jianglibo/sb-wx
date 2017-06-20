package com.jianglibo.wx.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "bootgroup", uniqueConstraints = { @UniqueConstraint(columnNames = "name")})
public class BootGroup extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7066615458934930195L;
	
	@NotNull
	private String name;
	
	public BootGroup() {
	}
	
	public BootGroup(String name) {
		this.name = name;
	}
	
	@ManyToOne
	private BootUser creator;
	
	@OneToMany(mappedBy="bootGroup")
	private List<GroupUserRelation> members = new ArrayList<>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<GroupUserRelation> getMembers() {
		return members;
	}

	public void setMembers(List<GroupUserRelation> members) {
		this.members = members;
	}

	public BootUser getCreator() {
		return creator;
	}

	public void setCreator(BootUser creator) {
		this.creator = creator;
	}
}
