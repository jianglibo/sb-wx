package com.jianglibo.wx.facade.jpa;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jianglibo.wx.domain.BootUser;
import com.jianglibo.wx.domain.MessageNotify;
import com.jianglibo.wx.facade.MessageNotifyFacadeRepository;
import com.jianglibo.wx.facade.Page;
import com.jianglibo.wx.katharsis.dto.MessageNotifyDto;
import com.jianglibo.wx.repository.MessageNotifyRepository;

@Component
public class MessageNotifyFacadeRepositoryImpl extends FacadeRepositoryBaseImpl<MessageNotify,MessageNotifyDto, MessageNotifyRepository> implements MessageNotifyFacadeRepository {

	@Autowired
	public MessageNotifyFacadeRepositoryImpl(MessageNotifyRepository jpaRepo) {
		super(jpaRepo);
	}

	@Override
	public MessageNotify newByDto(MessageNotifyDto dto) {
		return null;
	}

	@Override
	public Page<MessageNotify> findByBootUserAndNtype(BootUser user, String ntype) {
		List<MessageNotify> mns = getRepository().findByBootUserAndNtype(user, ntype);
		return new Page<>(mns.size(), mns);
	}

}
