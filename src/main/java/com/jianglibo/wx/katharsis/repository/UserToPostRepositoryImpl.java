package com.jianglibo.wx.katharsis.repository;

import org.springframework.stereotype.Component;

import com.jianglibo.wx.katharsis.dto.PostDto;
import com.jianglibo.wx.katharsis.dto.UserDto;

@Component
public class UserToPostRepositoryImpl extends RelationshipRepositoryBaseMine<UserDto, PostDto> {

	protected UserToPostRepositoryImpl() {
		super(UserDto.class, PostDto.class);
	}

}
