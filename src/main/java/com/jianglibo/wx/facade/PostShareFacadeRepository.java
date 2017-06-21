package com.jianglibo.wx.facade;

import java.util.List;

import com.jianglibo.wx.domain.BootGroup;
import com.jianglibo.wx.domain.BootUser;
import com.jianglibo.wx.domain.PostShare;
import com.jianglibo.wx.katharsis.dto.PostShareDto;


public interface PostShareFacadeRepository extends FacadeRepositoryBase<PostShare, PostShareDto> {
	
	/**
	 * find by followed mean the follow target is me. So the result is all I follower.
	 */
	List<PostShare> findByBootGroup(long groupId, long offset, Long limit, SortBroker...sortBrokers);
	
	long countByBootGroup(long groupId);
	
	/**
	 * find by follower mean the follower is me. So the result is all I followed.
	 */
	List<PostShare> findByBootUser(long userId, long offset, Long limit, SortBroker...sortBrokers);
	
	long countByBootUser(long userId);

	PostShare findByBootGroupAndBootUser(BootGroup gp, BootUser findOne);
	
	
}
