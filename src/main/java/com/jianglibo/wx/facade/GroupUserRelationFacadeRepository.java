package com.jianglibo.wx.facade;

import java.util.List;

import com.jianglibo.wx.domain.BootGroup;
import com.jianglibo.wx.domain.BootUser;
import com.jianglibo.wx.domain.GroupUserRelation;
import com.jianglibo.wx.katharsis.dto.GroupUserRelationDto;


public interface GroupUserRelationFacadeRepository extends FacadeRepositoryBase<GroupUserRelation, GroupUserRelationDto> {
	
	List<GroupUserRelation> findByBootGroup(BootGroup group, long offset, Long limit, SortBroker...sortBrokers);
	
	long countByBootGroup(BootGroup group);
	
	List<GroupUserRelation> findByBootUser(BootUser user, long offset, Long limit, SortBroker...sortBrokers);
	
	long countByBootUser(BootUser user);

	GroupUserRelation findByBootGroupAndBootUser(BootGroup gp, BootUser findOne);

	List<GroupUserRelation> findByBootGroup(BootGroup bg);
	
}
