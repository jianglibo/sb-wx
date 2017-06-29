package com.jianglibo.wx.katharsis.dto;

import javax.validation.constraints.NotNull;

import com.jianglibo.wx.annotation.DtoToEntity;
import com.jianglibo.wx.config.JsonApiResourceNames;
import com.jianglibo.wx.domain.GroupUserRelation;

import io.katharsis.resource.annotations.JsonApiResource;

@JsonApiResource(type = JsonApiResourceNames.GR_USER_RELATION)
@DtoToEntity(entityClass=GroupUserRelation.class)
public class GroupUserRelationDto extends DtoBase {
	
	@NotNull
	private UserDto bootUser;
	
	@NotNull
	private GroupDto bootGroup;

	public UserDto getBootUser() {
		return bootUser;
	}

	public void setBootUser(UserDto bootUser) {
		this.bootUser = bootUser;
	}

	public GroupDto getBootGroup() {
		return bootGroup;
	}

	public void setBootGroup(GroupDto bootGroup) {
		this.bootGroup = bootGroup;
	}


}
