package com.jianglibo.wx.katharsis.repository;

import org.springframework.stereotype.Component;

import com.jianglibo.wx.katharsis.dto.MediumDto;
import com.jianglibo.wx.katharsis.dto.PostDto;

@Component
public class PostToMediumRepositoryImpl extends RelationshipRepositoryBaseMine<PostDto, MediumDto> {
	
	protected PostToMediumRepositoryImpl() {
		super(PostDto.class, MediumDto.class);
	}
}
