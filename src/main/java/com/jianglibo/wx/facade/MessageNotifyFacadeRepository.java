package com.jianglibo.wx.facade;


import com.jianglibo.wx.domain.BootUser;
import com.jianglibo.wx.domain.MessageNotify;
import com.jianglibo.wx.katharsis.dto.MessageNotifyDto;


public interface MessageNotifyFacadeRepository extends FacadeRepositoryBase<MessageNotify, MessageNotifyDto> {
	MessageNotify findByBootUserAndNtype(BootUser user, String ntype);
	Page<MessageNotify> findByBootUser(BootUser bu);
}
