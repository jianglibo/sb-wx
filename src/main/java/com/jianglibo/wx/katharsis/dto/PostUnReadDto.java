package com.jianglibo.wx.katharsis.dto;

import javax.validation.constraints.NotNull;

import com.jianglibo.wx.domain.PostUnRead;

public class PostUnReadDto extends DtoBase<PostUnReadDto, PostUnRead>{

	@NotNull
	private PostDto post;
	
	@NotNull
	private UserDto bootUser;

	public PostDto getPost() {
		return post;
	}

	public void setPost(PostDto post) {
		this.post = post;
	}

	public UserDto getBootUser() {
		return bootUser;
	}

	public void setBootUser(UserDto bootUser) {
		this.bootUser = bootUser;
	}
}
