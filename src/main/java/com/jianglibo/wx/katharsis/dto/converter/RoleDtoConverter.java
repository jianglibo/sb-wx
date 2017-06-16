package com.jianglibo.wx.katharsis.dto.converter;

import org.springframework.stereotype.Component;

import com.jianglibo.wx.domain.Role;
import com.jianglibo.wx.katharsis.dto.RoleDto;

@Component
public class RoleDtoConverter implements DtoConverter<Role, RoleDto> {

	@Override
	public Role dot2Entity(RoleDto dto) {
		return null;
	}

	@Override
	public RoleDto entity2Dto(Role entity) {
		RoleDto dto = new RoleDto();
		dto.setId(entity.getId());
		dto.setName(entity.getName());
		dto.setCreatedAt(entity.getCreatedAt());
		return dto;
	}

}
