package com.jianglibo.wx.katharsis.repository;

import org.springframework.stereotype.Component;

import com.jianglibo.wx.katharsis.alter.RelationshipRepositoryBaseMy;
import com.jianglibo.wx.katharsis.dto.MySiteDto;
import com.jianglibo.wx.katharsis.dto.UserDto;

@Component
public class UserToMySiteRepository extends RelationshipRepositoryBaseMy<UserDto, MySiteDto> {

	protected UserToMySiteRepository() {
		super(UserDto.class, MySiteDto.class);
	}
}
