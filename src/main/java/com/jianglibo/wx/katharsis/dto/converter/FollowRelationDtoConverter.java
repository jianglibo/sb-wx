package com.jianglibo.wx.katharsis.dto.converter;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jianglibo.wx.domain.FollowRelation;
import com.jianglibo.wx.katharsis.dto.FollowRelationDto;
import com.jianglibo.wx.katharsis.dto.converter.DtoConverter.Scenario;

@Component
public class FollowRelationDtoConverter implements DtoConverter<FollowRelation, FollowRelationDto> {

	@Autowired
	private UserDtoConverter userConverter;
	
//	@Override
//	public FollowRelation dto2Entity(FollowRelationDto dto) {
//		FollowRelation entity = new FollowRelation();
//		BeanUtils.copyProperties(dto, entity);
//		return entity;
//	}

	@Override
	public FollowRelationDto entity2Dto(FollowRelation entity, Scenario scenario) {
		FollowRelationDto dto = new FollowRelationDto();
		dto.setFollower(userConverter.entity2Dto(entity.getFollower(), scenario));
		dto.setBefollowed(userConverter.entity2Dto(entity.getFollowed(), scenario));
		return dto;
	}
}
