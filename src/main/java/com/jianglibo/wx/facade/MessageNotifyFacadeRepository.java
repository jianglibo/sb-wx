package com.jianglibo.wx.facade;


import java.util.List;

import com.jianglibo.wx.domain.BootUser;
import com.jianglibo.wx.domain.MessageNotify;
import com.jianglibo.wx.katharsis.dto.MessageNotifyDto;


public interface MessageNotifyFacadeRepository extends FacadeRepositoryBase<MessageNotify, MessageNotifyDto> {
	List<MessageNotify> findByBootUserAndNtype(BootUser user, String ntype);
	long countByBootUserAndNtype(BootUser user, String ntype);
}
