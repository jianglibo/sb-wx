package com.jianglibo.wx.katharsis.dto.converter;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jianglibo.wx.domain.Post;
import com.jianglibo.wx.katharsis.dto.PostDto;

@Component
public class PostDtoConverter implements DtoConverter<Post, PostDto> {
	
	@Autowired
	private UserDtoConverter userConverter;

	@Override
	public Post dot2Entity(PostDto dto) {
		Post entity = new Post();
		BeanUtils.copyProperties(dto, entity, "creator", "media");
		return entity;
	}

	@Override
	public PostDto entity2Dto(Post entity) {
		PostDto dto = new PostDto();
		BeanUtils.copyProperties(entity, dto, "creator", "media");
		dto.setCreator(userConverter.entity2Dto(entity.getCreator()));
		return dto;
	}

}
