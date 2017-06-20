package com.jianglibo.wx.facade;

import java.util.List;

import com.jianglibo.wx.domain.FollowRelation;
import com.jianglibo.wx.katharsis.dto.FollowRelationDto;


public interface FollowRelationFacadeRepository extends FacadeRepositoryBase<FollowRelation, FollowRelationDto> {
	
	/**
	 * find by followed mean the follow target is me. So the result is all I follower.
	 */
	List<FollowRelation> findByFollowed(long userId, long offset, Long limit, SortBroker...sortBrokers);
	
	long countByFollowed(long userId, long offset, Long limit, SortBroker...sortBrokers);
	
	
	/**
	 * find by follower mean the follower is me. So the result is all I followed.
	 */
	List<FollowRelation> findByFollower(long userId, long offset, Long limit, SortBroker...sortBrokers);
	
	long countByFollower(long userId, long offset, Long limit, SortBroker...sortBrokers);
	
	
}
