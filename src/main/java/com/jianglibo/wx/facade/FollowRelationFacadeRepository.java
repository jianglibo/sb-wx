package com.jianglibo.wx.facade;

import java.util.List;

import com.jianglibo.wx.domain.FollowRelation;
import com.jianglibo.wx.katharsis.dto.FollowRelationDto;


public interface FollowRelationFacadeRepository extends FacadeRepositoryBase<FollowRelation, FollowRelationDto> {
	
	List<FollowRelation> whosFollowedMe(long userId, long offset, Long limit, SortBroker...sortBrokers);
	
	long countWhosFollowedMe(long userId, long offset, Long limit, SortBroker...sortBrokers);
	
	List<FollowRelation> whosIFollowedTo(long userId, long offset, Long limit, SortBroker...sortBrokers);
	
	long countWhosIFollowedTo(long userId, long offset, Long limit, SortBroker...sortBrokers);
}
