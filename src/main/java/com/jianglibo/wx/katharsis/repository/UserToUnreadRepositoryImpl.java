package com.jianglibo.wx.katharsis.repository;

import org.springframework.stereotype.Component;

import com.jianglibo.wx.katharsis.dto.UnreadDto;
import com.jianglibo.wx.katharsis.dto.UserDto;

@Component
public class UserToUnreadRepositoryImpl extends RelationshipRepositoryBaseMine<UserDto, UnreadDto> {
	
	protected UserToUnreadRepositoryImpl() {
		super(UserDto.class, UnreadDto.class);
	}

}
