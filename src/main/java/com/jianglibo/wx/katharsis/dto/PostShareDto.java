package com.jianglibo.wx.katharsis.dto;

import javax.validation.constraints.NotNull;

import com.jianglibo.wx.annotation.DtoToEntity;
import com.jianglibo.wx.config.JsonApiResourceNames;
import com.jianglibo.wx.domain.PostShare;

import io.katharsis.resource.annotations.JsonApiResource;

@JsonApiResource(type = JsonApiResourceNames.POST_SHARE)
@DtoToEntity(entityClass=PostShare.class)
public class PostShareDto extends DtoBase {
	
	@NotNull
	private UserDto bootUser;
	
	@NotNull
	private PostDto post;

	public UserDto getBootUser() {
		return bootUser;
	}

	public void setBootUser(UserDto bootUser) {
		this.bootUser = bootUser;
	}

	public PostDto getPost() {
		return post;
	}

	public void setPost(PostDto post) {
		this.post = post;
	}


}
