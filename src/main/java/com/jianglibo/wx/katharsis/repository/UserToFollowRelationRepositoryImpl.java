package com.jianglibo.wx.katharsis.repository;

import org.springframework.stereotype.Component;

import com.jianglibo.wx.katharsis.dto.FollowRelationDto;
import com.jianglibo.wx.katharsis.dto.UserDto;

@Component
public class UserToFollowRelationRepositoryImpl extends RelationshipRepositoryBaseMine<UserDto, FollowRelationDto> {

	protected UserToFollowRelationRepositoryImpl() {
		super(UserDto.class, FollowRelationDto.class);
	}

}
