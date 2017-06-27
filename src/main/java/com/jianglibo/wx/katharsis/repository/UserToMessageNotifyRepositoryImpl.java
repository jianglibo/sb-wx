package com.jianglibo.wx.katharsis.repository;

import org.springframework.stereotype.Component;

import com.jianglibo.wx.katharsis.dto.MessageNotifyDto;
import com.jianglibo.wx.katharsis.dto.UserDto;

@Component
public class UserToMessageNotifyRepositoryImpl extends RelationshipRepositoryBaseMine<UserDto, MessageNotifyDto> {
	
	protected UserToMessageNotifyRepositoryImpl() {
		super(UserDto.class, MessageNotifyDto.class);
	}
}
