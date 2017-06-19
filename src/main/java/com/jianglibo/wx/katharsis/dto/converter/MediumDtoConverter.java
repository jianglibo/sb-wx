package com.jianglibo.wx.katharsis.dto.converter;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.jianglibo.wx.domain.Medium;
import com.jianglibo.wx.katharsis.dto.MediumDto;

@Component
public class MediumDtoConverter implements DtoConverter<Medium, MediumDto> {

	@Override
	public Medium dot2Entity(MediumDto dto) {
		Medium entity = new Medium();
		BeanUtils.copyProperties(dto, entity);
		return entity;
	}

	@Override
	public MediumDto entity2Dto(Medium entity) {
		MediumDto dto = new MediumDto();
		BeanUtils.copyProperties(entity, dto, "creator");
		return dto;
	}

}
