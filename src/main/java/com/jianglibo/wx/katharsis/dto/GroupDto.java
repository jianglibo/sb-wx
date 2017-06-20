package com.jianglibo.wx.katharsis.dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


import com.jianglibo.wx.annotation.DtoToEntity;
import com.jianglibo.wx.config.JsonApiResourceNames;
import com.jianglibo.wx.domain.BootGroup;

import io.katharsis.resource.annotations.JsonApiRelation;
import io.katharsis.resource.annotations.JsonApiResource;
import io.katharsis.resource.annotations.LookupIncludeBehavior;
import io.katharsis.resource.annotations.SerializeType;

@JsonApiResource(type = JsonApiResourceNames.BOOT_GROUP)
@DtoToEntity(entityClass=BootGroup.class)
public class GroupDto extends DtoBase<GroupDto, BootGroup>{
	
	@NotNull
	@Size(min=3, max=30)
	private String name;
	
	@JsonApiRelation(lookUp=LookupIncludeBehavior.NONE,serialize=SerializeType.LAZY, opposite="bootGroups")
	private List<UserDto> members = new ArrayList<>();
	
	@JsonApiRelation(lookUp=LookupIncludeBehavior.NONE,serialize=SerializeType.LAZY, opposite="ownedGroups")
	private UserDto creator;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return String.format("[%s,%s]", getId(), getName());
	}

	public List<UserDto> getMembers() {
		return members;
	}

	public void setMembers(List<UserDto> members) {
		this.members = members;
	}

	public UserDto getCreator() {
		return creator;
	}

	public void setCreator(UserDto creator) {
		this.creator = creator;
	}
}
