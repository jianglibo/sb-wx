package com.jianglibo.wx.facade;

import java.util.List;

import com.jianglibo.wx.domain.BootGroup;
import com.jianglibo.wx.domain.BootUser;
import com.jianglibo.wx.domain.GroupUserRelation;
import com.jianglibo.wx.katharsis.dto.GroupUserRelationDto;


public interface GroupUserRelationFacadeRepository extends FacadeRepositoryBase<GroupUserRelation, GroupUserRelationDto> {
	
	/**
	 * find by followed mean the follow target is me. So the result is all I follower.
	 */
	List<GroupUserRelation> findByBootGroup(long groupId, long offset, Long limit, SortBroker...sortBrokers);
	
	long countByBootGroup(long groupId);
	
	/**
	 * find by follower mean the follower is me. So the result is all I followed.
	 */
	List<GroupUserRelation> findByBootUser(long userId, long offset, Long limit, SortBroker...sortBrokers);
	
	long countByBootUser(long userId);

	GroupUserRelation findByBootGroupAndBootUser(BootGroup gp, BootUser findOne);
	
	
}
