package com.jianglibo.wx.katharsis.repository;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jianglibo.wx.domain.BootUser;
import com.jianglibo.wx.domain.MessageNotify;
import com.jianglibo.wx.domain.Post;
import com.jianglibo.wx.facade.BootUserFacadeRepository;
import com.jianglibo.wx.facade.MessageNotifyFacadeRepository;
import com.jianglibo.wx.facade.Page;
import com.jianglibo.wx.katharsis.dto.MessageNotifyDto;
import com.jianglibo.wx.katharsis.dto.UserDto;
import com.jianglibo.wx.katharsis.dto.converter.DtoConverter.Scenario;
import com.jianglibo.wx.katharsis.dto.converter.MessageNotifyDtoConverter;
import com.jianglibo.wx.katharsis.repository.MessageNotifyDtoRepository.MessageNotifyDtoList;
import com.jianglibo.wx.util.QuerySpecUtil.RelationQuery;

import io.katharsis.queryspec.QuerySpec;

@Component
public class MessageNotifyDtoRepositoryImpl  extends DtoRepositoryBase<MessageNotifyDto, MessageNotifyDtoList, MessageNotify, MessageNotifyFacadeRepository> implements MessageNotifyDtoRepository {
	
	@Autowired
	private BootUserFacadeRepository userRepo;
	
	@Autowired
	private MessageNotifyFacadeRepository mnRepo;
	
	@Autowired
	public MessageNotifyDtoRepositoryImpl(MessageNotifyFacadeRepository repository, MessageNotifyDtoConverter converter) {
		super(MessageNotifyDto.class, MessageNotifyDtoList.class, MessageNotify.class, repository, converter);
	}

	@Override
	protected MessageNotifyDtoList findAllWithQuerySpec(QuerySpec querySpec) {
		return null;
	}

	@Override
	protected List<String> checkAllSortableFieldAllowed(QuerySpec querySpec) {
		return null;
	}

	@Override
	protected MessageNotifyDtoList findWithRelationAndSpec(RelationQuery rq, QuerySpec querySpec) {
		if ("user".equals(rq.getRelationName())) {
			UserDto udto = new UserDto(rq.getRelationIds().get(0));
			BootUser bu = userRepo.findOne(udto.getId(), true);
			Page<MessageNotify> mns = mnRepo.findByBootUser(bu, Post.class.getSimpleName());
			MessageNotifyDtoList mnl = convertToResourceList(mns, Scenario.FIND_LIST);
			mnl.forEach(mn -> mn.setUser(udto));
			return mnl;
		}
		throw getUnsupportRelationException("messagenotify", rq.getRelationName());
	}
}
