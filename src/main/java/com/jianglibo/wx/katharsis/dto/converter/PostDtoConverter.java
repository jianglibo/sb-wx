package com.jianglibo.wx.katharsis.dto.converter;

import org.springframework.stereotype.Component;

import com.jianglibo.wx.domain.Post;
import com.jianglibo.wx.katharsis.dto.PostDto;

@Component
public class PostDtoConverter implements DtoConverter<Post, PostDto> {

	@Override
	public Post dot2Entity(PostDto dto) {
		Post entity = new Post();
		entity.setContent(dto.getContent());
		entity.setTitle(dto.getTitle());
		return entity;
	}

	@Override
	public PostDto entity2Dto(Post entity) {
		PostDto dto = new PostDto();
		dto.setId(entity.getId());
		dto.setContent(entity.getContent());
		dto.setTitle(entity.getTitle());
		dto.setCreatedAt(entity.getCreatedAt());
		return dto;
	}

}
