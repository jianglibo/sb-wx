package com.jianglibo.wx.katharsis.dto;

import javax.validation.constraints.NotNull;

import com.jianglibo.wx.annotation.DtoToEntity;
import com.jianglibo.wx.config.JsonApiResourceNames;
import com.jianglibo.wx.domain.Post;

import io.katharsis.resource.annotations.JsonApiResource;

@JsonApiResource(type = JsonApiResourceNames.POST)
@DtoToEntity(entityClass=Post.class)
public class PostDto extends DtoBase<PostDto, Post>{
	
	@NotNull
	private String title;
	
	private String content;
	
	@Override
	public String toString() {
		return String.format("[%s,%s]", getId(), getTitle());
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
