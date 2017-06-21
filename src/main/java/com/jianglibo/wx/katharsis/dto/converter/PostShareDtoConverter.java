package com.jianglibo.wx.katharsis.dto.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jianglibo.wx.domain.PostShare;
import com.jianglibo.wx.katharsis.dto.PostShareDto;

@Component
public class PostShareDtoConverter implements DtoConverter<PostShare, PostShareDto> {

	@Autowired
	private UserDtoConverter userConverter;
	
	@Autowired
	private PostDtoConverter postConverter;


	@Override
	public PostShareDto entity2Dto(PostShare entity) {
		PostShareDto dto = new PostShareDto();
		dto.setBootUser(userConverter.entity2Dto(entity.getBootUser()));
		dto.setPost(postConverter.entity2Dto(entity.getPost()));
		return dto;
	}
}
