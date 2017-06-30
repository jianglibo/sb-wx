package com.jianglibo.wx.facade;

import java.util.List;

import com.jianglibo.wx.domain.BootUser;
import com.jianglibo.wx.domain.Unread;
import com.jianglibo.wx.katharsis.dto.UnreadDto;


public interface UnreadFacadeRepository extends FacadeRepositoryBase<Unread, UnreadDto> {
    List<Unread> findByBootUserAndType(BootUser user, String type, long offset, Long limit, SortBroker...sortBrokers);
    long countByBootUserAndType(BootUser user, String type);
    List<Unread> findAll();
	Unread findByBootUserAndTypeAndObid(BootUser user, String type, Long id);
}
