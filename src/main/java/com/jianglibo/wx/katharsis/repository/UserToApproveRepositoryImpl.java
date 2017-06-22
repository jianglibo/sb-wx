package com.jianglibo.wx.katharsis.repository;

import org.springframework.stereotype.Component;

import com.jianglibo.wx.katharsis.dto.ApproveDto;
import com.jianglibo.wx.katharsis.dto.UserDto;

@Component
public class UserToApproveRepositoryImpl extends RelationshipRepositoryBaseMine<UserDto, ApproveDto> {
	
	protected UserToApproveRepositoryImpl() {
		super(UserDto.class, ApproveDto.class);
	}

}
