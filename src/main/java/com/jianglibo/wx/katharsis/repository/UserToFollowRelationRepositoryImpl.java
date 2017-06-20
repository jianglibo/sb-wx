package com.jianglibo.wx.katharsis.repository;

import org.springframework.stereotype.Component;

import com.jianglibo.wx.katharsis.dto.FollowRelationDto;
import com.jianglibo.wx.katharsis.dto.UserDto;

import io.katharsis.repository.RelationshipRepositoryBase;

@Component
public class UserToFollowRelationRepositoryImpl extends RelationshipRepositoryBase<UserDto, Long, FollowRelationDto, Long> {

	protected UserToFollowRelationRepositoryImpl() {
		super(UserDto.class, FollowRelationDto.class);
	}

}
