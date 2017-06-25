package com.jianglibo.wx.katharsis.dto;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.jianglibo.wx.annotation.DtoToEntity;
import com.jianglibo.wx.config.JsonApiResourceNames;
import com.jianglibo.wx.domain.Post;

import io.katharsis.resource.annotations.JsonApiRelation;
import io.katharsis.resource.annotations.JsonApiResource;
import io.katharsis.resource.annotations.LookupIncludeBehavior;
import io.katharsis.resource.annotations.SerializeType;

@JsonApiResource(type = JsonApiResourceNames.POST)
@DtoToEntity(entityClass=Post.class)
public class PostDto extends DtoBase<PostDto, Post>{
	
	@NotNull
	private String title;
	
	private String content;
	
	@JsonApiRelation(lookUp=LookupIncludeBehavior.AUTOMATICALLY_WHEN_NULL,serialize=SerializeType.LAZY, opposite="posts")
	private UserDto creator;
	
	@JsonApiRelation(lookUp=LookupIncludeBehavior.AUTOMATICALLY_WHEN_NULL,serialize=SerializeType.LAZY, opposite="post")
	private List<MediumDto> media;
	
	@JsonApiRelation(lookUp=LookupIncludeBehavior.AUTOMATICALLY_WHEN_NULL,serialize=SerializeType.LAZY, opposite="receivedPosts")
	private List<UserDto> sharedUsers;
	
	public PostDto() {}
	
	public PostDto(Long id) {
		super(id);
	}
	
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

	public UserDto getCreator() {
		return creator;
	}

	public void setCreator(UserDto creator) {
		this.creator = creator;
	}

	public List<MediumDto> getMedia() {
		return media;
	}

	public void setMedia(List<MediumDto> media) {
		this.media = media;
	}

	public List<UserDto> getSharedUsers() {
		return sharedUsers;
	}

	public void setSharedUsers(List<UserDto> sharedUsers) {
		this.sharedUsers = sharedUsers;
	}
}
