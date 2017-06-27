package com.jianglibo.wx.katharsis.dto.converter;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.jianglibo.wx.domain.Unread;
import com.jianglibo.wx.katharsis.dto.UnreadDto;

@Component
public class UnreadDtoConverter implements DtoConverter<Unread, UnreadDto> {

	@Override
	public UnreadDto entity2Dto(Unread entity,Scenario scenario) {
		UnreadDto dto = new UnreadDto();
		BeanUtils.copyProperties(entity, dto, "bootUser");
		return dto;
	}

}
