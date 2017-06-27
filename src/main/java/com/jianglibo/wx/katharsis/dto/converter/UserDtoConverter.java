package com.jianglibo.wx.katharsis.dto.converter;

import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jianglibo.wx.domain.BootUser;
import com.jianglibo.wx.katharsis.dto.UserDto;

@Component
public class UserDtoConverter implements DtoConverter<BootUser, UserDto> {
	
	@Autowired
	private RoleDtoConverter roleConverter;

	@Override
	public UserDto entity2Dto(BootUser entity, Scenario scenario) {
		// 18 fields.
		UserDto dto = new UserDto();
		BeanUtils.copyProperties(entity, dto, "password", "roles", "followers", "followeds", "media", "posts", "joinedGroups", "ownedGroups", "receivedPosts", "sentApproves", "receivedApproves", "notifies", "unreads");
    	dto.setRoles(entity.getRoles().stream().map(r -> roleConverter.entity2Dto(r, scenario)).collect(Collectors.toList()));
    	return dto;
	}

}
