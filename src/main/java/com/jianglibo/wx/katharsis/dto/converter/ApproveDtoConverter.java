package com.jianglibo.wx.katharsis.dto.converter;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jianglibo.wx.domain.Approve;
import com.jianglibo.wx.katharsis.dto.ApproveDto;

@Component
public class ApproveDtoConverter implements DtoConverter<Approve, ApproveDto> {

	@Autowired
	private UserDtoConverter userConverter;
	
	@Override
	public ApproveDto entity2Dto(Approve entity, Scenario scenario) {
		ApproveDto dto = new ApproveDto();
		BeanUtils.copyProperties(entity, dto, "requester", "receiver");
//		if (entity.getRequester() != null) {
//			dto.setRequester(userConverter.entity2Dto(entity.getRequester(), scenario));
//		}
//		if (entity.getReceiver() != null) {
//			dto.setReceiver(userConverter.entity2Dto(entity.getReceiver(), scenario));
//		}
		return dto;
	}
}
