package com.jianglibo.wx.katharsis.dto.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jianglibo.wx.domain.GroupUserRelation;
import com.jianglibo.wx.katharsis.dto.GroupUserRelationDto;

@Component
public class GroupUserRelationDtoConverter implements DtoConverter<GroupUserRelation, GroupUserRelationDto> {

	@Autowired
	private UserDtoConverter userConverter;
	
	@Autowired
	private GroupDtoConverter groupConverter;


	@Override
	public GroupUserRelationDto entity2Dto(GroupUserRelation entity) {
		GroupUserRelationDto dto = new GroupUserRelationDto();
		dto.setBootUser(userConverter.entity2Dto(entity.getBootUser()));
		dto.setBootGroup(groupConverter.entity2Dto(entity.getBootGroup()));
		return dto;
	}
}
