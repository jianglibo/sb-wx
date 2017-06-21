package com.jianglibo.wx.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.jianglibo.wx.domain.BootUser;
import com.jianglibo.wx.domain.FollowRelation;

public interface FollowRelationRepository extends RepositoryBase<FollowRelation> {

	List<FollowRelation> findAllByFollowed(BootUser followTarget, Pageable pageable);
	
	long countByFollowed(BootUser followTarget);
	
	List<FollowRelation> findAllByFollower(BootUser followTarget, Pageable pageable);
	
	long countByFollower(BootUser followTarget);

	FollowRelation findByFollowedAndFollower(BootUser befollowed, BootUser follower);
}
