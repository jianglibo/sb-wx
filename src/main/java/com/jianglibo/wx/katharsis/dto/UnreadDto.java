package com.jianglibo.wx.katharsis.dto;

import javax.validation.constraints.NotNull;

import com.jianglibo.wx.annotation.DtoToEntity;
import com.jianglibo.wx.config.JsonApiResourceNames;
import com.jianglibo.wx.domain.Unread;

import io.katharsis.resource.annotations.JsonApiRelation;
import io.katharsis.resource.annotations.JsonApiResource;
import io.katharsis.resource.annotations.LookupIncludeBehavior;
import io.katharsis.resource.annotations.SerializeType;

@JsonApiResource(type = JsonApiResourceNames.UNREAD)
@DtoToEntity(entityClass=Unread.class)
public class UnreadDto extends DtoBase<UnreadDto, Unread>{

	@NotNull
	private long obid;
	
	@NotNull
	private String type;
	
	@NotNull
	@JsonApiRelation(lookUp=LookupIncludeBehavior.NONE,serialize=SerializeType.LAZY, opposite="unreads")
	private UserDto user;

	public UserDto getUser() {
		return user;
	}

	public void setUser(UserDto user) {
		this.user = user;
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
	
	
}
