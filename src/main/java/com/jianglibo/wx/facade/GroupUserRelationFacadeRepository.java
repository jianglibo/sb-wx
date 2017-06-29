package com.jianglibo.wx.facade;

import com.jianglibo.wx.domain.BootGroup;
import com.jianglibo.wx.domain.BootUser;
import com.jianglibo.wx.domain.GroupUserRelation;
import com.jianglibo.wx.katharsis.dto.GroupUserRelationDto;


public interface GroupUserRelationFacadeRepository extends FacadeRepositoryBase<GroupUserRelation, GroupUserRelationDto> {
	
	Page<GroupUserRelation> findByBootGroup(BootGroup group, PageFacade pf);
	
	Page<GroupUserRelation> findByBootUser(BootUser user, PageFacade pf);

	GroupUserRelation findByBootGroupAndBootUser(BootGroup group, BootUser user);
	
	void deleteByBootGroupAndBootUser(BootGroup group, BootUser user);

	void addRelation(BootGroup group, BootUser user);
	
}
