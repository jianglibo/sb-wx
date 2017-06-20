package com.jianglibo.wx.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.jianglibo.wx.domain.BootUser;
import com.jianglibo.wx.domain.FollowRelation;

public interface FollowRelationRepository extends RepositoryBase<FollowRelation> {

	List<FollowRelation> findAllByBefollowed(BootUser followTarget, Pageable pageable);
	
	long countByBefollowed(BootUser followTarget);
	
	List<FollowRelation> findAllByFollower(BootUser followTarget, Pageable pageable);
	
	long countByFollower(BootUser followTarget);
}
