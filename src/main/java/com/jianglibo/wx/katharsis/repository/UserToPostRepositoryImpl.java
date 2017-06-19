package com.jianglibo.wx.katharsis.repository;

import org.springframework.stereotype.Component;

import com.jianglibo.wx.katharsis.dto.PostDto;
import com.jianglibo.wx.katharsis.dto.UserDto;

import io.katharsis.repository.RelationshipRepositoryBase;

@Component
public class UserToPostRepositoryImpl extends RelationshipRepositoryBase<UserDto, Long, PostDto, Long> {

	protected UserToPostRepositoryImpl() {
		super(UserDto.class, PostDto.class);
	}

}
