package com.jianglibo.wx.katharsis.dto.converter;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.jianglibo.wx.domain.Role;
import com.jianglibo.wx.katharsis.dto.RoleDto;

@Component
public class RoleDtoConverter implements DtoConverter<Role, RoleDto> {

	@Override
	public RoleDto entity2Dto(Role entity,Scenario scenario) {
		RoleDto dto = new RoleDto();
		BeanUtils.copyProperties(entity, dto);
		return dto;
	}

}
