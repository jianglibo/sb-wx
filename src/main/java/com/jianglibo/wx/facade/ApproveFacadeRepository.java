package com.jianglibo.wx.facade;


import java.util.List;

import com.jianglibo.wx.domain.BootUser;
import com.jianglibo.wx.domain.Approve;
import com.jianglibo.wx.katharsis.dto.ApproveDto;


public interface ApproveFacadeRepository extends FacadeRepositoryBase<Approve, ApproveDto> {
	List<Approve> findSent(BootUser user, long offset, Long limit, SortBroker...sortBrokers);
	long countSend(BootUser user);
	
	List<Approve> findReceived(BootUser user, long offset, Long limit, SortBroker...sortBrokers);
	long countReceived(BootUser user);

}
