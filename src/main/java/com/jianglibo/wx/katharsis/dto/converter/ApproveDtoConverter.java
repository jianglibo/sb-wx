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
	public ApproveDto entity2Dto(Approve entity) {
		ApproveDto dto = new ApproveDto();
		BeanUtils.copyProperties(entity, dto, "requester", "receiver");
		dto.setRequester(userConverter.entity2Dto(entity.getRequester()));
		dto.setReceiver(userConverter.entity2Dto(entity.getReceiver()));
		return dto;
	}
}
