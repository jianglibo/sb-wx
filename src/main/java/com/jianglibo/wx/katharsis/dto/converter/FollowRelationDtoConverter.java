package com.jianglibo.wx.katharsis.dto.converter;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jianglibo.wx.domain.FollowRelation;
import com.jianglibo.wx.katharsis.dto.FollowRelationDto;

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
	public FollowRelationDto entity2Dto(FollowRelation entity) {
		FollowRelationDto dto = new FollowRelationDto();
		dto.setFollower(userConverter.entity2Dto(entity.getFollower()));
		dto.setBefollowed(userConverter.entity2Dto(entity.getBefollowed()));
		return dto;
	}
}
