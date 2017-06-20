package com.jianglibo.wx.katharsis.repository;

import org.springframework.stereotype.Component;

import com.jianglibo.wx.katharsis.dto.UserDto;

import io.katharsis.repository.RelationshipRepositoryBase;

@Component
public class UserToUserRepositoryImpl extends RelationshipRepositoryBase<UserDto, Long, UserDto, Long> {

	protected UserToUserRepositoryImpl() {
		super(UserDto.class, UserDto.class);
	}

}
