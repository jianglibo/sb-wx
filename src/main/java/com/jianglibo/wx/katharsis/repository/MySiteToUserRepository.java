package com.jianglibo.wx.katharsis.repository;

import org.springframework.stereotype.Component;

import com.jianglibo.wx.katharsis.alter.RelationshipRepositoryBaseMy;
import com.jianglibo.wx.katharsis.dto.MySiteDto;
import com.jianglibo.wx.katharsis.dto.UserDto;


@Component
public class MySiteToUserRepository extends RelationshipRepositoryBaseMy<MySiteDto, UserDto> {

	protected MySiteToUserRepository() {
		super( MySiteDto.class, UserDto.class);
	}
}
