package com.jianglibo.wx.repository;

import java.util.List;

import com.jianglibo.wx.domain.BootUser;
import com.jianglibo.wx.domain.MessageNotify;

public interface MessageNotifyRepository extends RepositoryBase<MessageNotify> {
	MessageNotify findByBootUserAndNtype(BootUser user, String ntype);
	List<MessageNotify> findByBootUser(BootUser user);
}
