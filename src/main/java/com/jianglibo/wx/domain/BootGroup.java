package com.jianglibo.wx.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
	
	private String description;
	
	private boolean openToAll;
	
	private String thumbUrl;
	
	@ManyToOne
	private BootUser creator;
	
	@OneToMany(mappedBy="bootGroup", fetch=FetchType.LAZY, cascade=CascadeType.REMOVE)
	private List<GroupUserRelation> members = new ArrayList<>();
	
	public BootGroup() {
	}
	
	public BootGroup(String name) {
		this.name = name;
	}

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getThumbUrl() {
		return thumbUrl;
	}

	public void setThumbUrl(String thumbUrl) {
		this.thumbUrl = thumbUrl;
	}

	public boolean isOpenToAll() {
		return openToAll;
	}

	public void setOpenToAll(boolean openToAll) {
		this.openToAll = openToAll;
	}
}
