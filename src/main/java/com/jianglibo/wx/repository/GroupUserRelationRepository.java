package com.jianglibo.wx.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.jianglibo.wx.domain.BootGroup;
import com.jianglibo.wx.domain.BootUser;
import com.jianglibo.wx.domain.GroupUserRelation;

public interface GroupUserRelationRepository extends RepositoryBase<GroupUserRelation> {

	Page<GroupUserRelation> findAllByBootUser(BootUser user, Pageable pageable);
	
	Page<GroupUserRelation> findAllByBootGroup(BootGroup group, Pageable pageable);
	
	GroupUserRelation findByBootGroupAndBootUser(BootGroup group, BootUser user);

}
