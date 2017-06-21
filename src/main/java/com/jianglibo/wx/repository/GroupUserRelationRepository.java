package com.jianglibo.wx.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.jianglibo.wx.domain.BootGroup;
import com.jianglibo.wx.domain.BootUser;
import com.jianglibo.wx.domain.GroupUserRelation;

public interface GroupUserRelationRepository extends RepositoryBase<GroupUserRelation> {

	List<GroupUserRelation> findAllByBootUser(BootUser user, Pageable pageable);
	
	long countByBootUser(BootUser user);
	
	List<GroupUserRelation> findAllByBootGroup(BootGroup group, Pageable pageable);
	
	long countByBootGroup(BootGroup group);

	GroupUserRelation findByBootGroupAndBootUser(BootGroup group, BootUser user);
}
