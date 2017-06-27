package com.jianglibo.wx.katharsis.dto.converter;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.jianglibo.wx.domain.MessageNotify;
import com.jianglibo.wx.katharsis.dto.MessageNotifyDto;

@Component
public class MessageNotifyDtoConverter implements DtoConverter<MessageNotify, MessageNotifyDto> {

	@Override
	public MessageNotifyDto entity2Dto(MessageNotify entity,Scenario scenario) {
		MessageNotifyDto dto = new MessageNotifyDto();
		BeanUtils.copyProperties(entity, dto, "user");
		return dto;
	}

}
