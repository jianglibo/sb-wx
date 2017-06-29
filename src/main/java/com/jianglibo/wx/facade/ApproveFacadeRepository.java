package com.jianglibo.wx.facade;


import com.jianglibo.wx.domain.BootUser;
import com.jianglibo.wx.domain.Approve;
import com.jianglibo.wx.katharsis.dto.ApproveDto;


public interface ApproveFacadeRepository extends FacadeRepositoryBase<Approve, ApproveDto> {
	
	Page<Approve> findSent(BootUser user, PageFacade pf);
	
	Page<Approve> findReceived(BootUser user, PageFacade pf);
}
