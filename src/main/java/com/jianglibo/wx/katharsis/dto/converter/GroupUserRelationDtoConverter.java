package com.jianglibo.wx.katharsis.dto.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jianglibo.wx.domain.GroupUserRelation;
import com.jianglibo.wx.katharsis.dto.GroupUserRelationDto;
import com.jianglibo.wx.katharsis.dto.converter.DtoConverter.Scenario;

@Component
public class GroupUserRelationDtoConverter implements DtoConverter<GroupUserRelation, GroupUserRelationDto> {

	@Autowired
	private UserDtoConverter userConverter;
	
	@Autowired
	private GroupDtoConverter groupConverter;


	@Override
	public GroupUserRelationDto entity2Dto(GroupUserRelation entity, Scenario scenario) {
		GroupUserRelationDto dto = new GroupUserRelationDto();
		dto.setBootUser(userConverter.entity2Dto(entity.getBootUser(), scenario));
		dto.setBootGroup(groupConverter.entity2Dto(entity.getBootGroup(), scenario));
		return dto;
	}
}
