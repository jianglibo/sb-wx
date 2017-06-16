package com.jianglibo.wx.katharsis.dto.converter;

import org.springframework.stereotype.Component;

import com.jianglibo.wx.domain.Medium;
import com.jianglibo.wx.katharsis.dto.MediumDto;

@Component
public class MediumDtoConverter implements DtoConverter<Medium, MediumDto> {

	@Override
	public Medium dot2Entity(MediumDto dto) {
		Medium entity = new Medium();
		entity.setContentType(dto.getContentType());
		entity.setLocalPath(dto.getLocalPath());
		entity.setSize(dto.getSize());
		entity.setUrl(dto.getUrl());
		return entity;
	}

	@Override
	public MediumDto entity2Dto(Medium entity) {
		MediumDto dto = new MediumDto();
		dto.setId(entity.getId());
		dto.setContentType(entity.getContentType());
		dto.setCreatedAt(entity.getCreatedAt());
		dto.setLocalPath(entity.getLocalPath());
		dto.setUrl(entity.getUrl());
		return dto;
	}

}
