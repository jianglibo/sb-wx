package com.jianglibo.wx.katharsis.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.jianglibo.wx.annotation.DtoToEntity;
import com.jianglibo.wx.config.JsonApiResourceNames;
import com.jianglibo.wx.domain.Role;

import io.katharsis.resource.annotations.JsonApiResource;

@JsonApiResource(type = JsonApiResourceNames.ROLE)
@DtoToEntity(entityClass=Role.class)
public class RoleDto extends DtoBase<RoleDto, Role>{
	
	@NotNull
	@Size(min=3, max=30)
	private String name;

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
}
