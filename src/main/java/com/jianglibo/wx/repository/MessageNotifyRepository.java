package com.jianglibo.wx.repository;

import java.util.List;

import com.jianglibo.wx.domain.BootUser;
import com.jianglibo.wx.domain.MessageNotify;

public interface MessageNotifyRepository extends RepositoryBase<MessageNotify> {
	List<MessageNotify> findByBootUserAndNtype(BootUser user, String ntype);
}
