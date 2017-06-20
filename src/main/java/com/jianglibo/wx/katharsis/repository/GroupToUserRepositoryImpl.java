package com.jianglibo.wx.katharsis.repository;

import org.springframework.stereotype.Component;

import com.jianglibo.wx.katharsis.dto.UserDto;
import com.jianglibo.wx.katharsis.dto.GroupDto;

import io.katharsis.repository.RelationshipRepositoryBase;

@Component
public class GroupToUserRepositoryImpl extends RelationshipRepositoryBase<GroupDto, Long, UserDto, Long> {

	protected GroupToUserRepositoryImpl() {
		super(GroupDto.class, UserDto.class);
	}

}
