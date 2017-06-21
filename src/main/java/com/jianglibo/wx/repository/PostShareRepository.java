package com.jianglibo.wx.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.jianglibo.wx.domain.BootGroup;
import com.jianglibo.wx.domain.BootUser;
import com.jianglibo.wx.domain.PostShare;

public interface PostShareRepository extends RepositoryBase<PostShare> {

	List<PostShare> findAllByBootUser(BootUser user, Pageable pageable);
	
	long countByBootUser(BootUser user);
	
	List<PostShare> findAllByBootGroup(BootGroup group, Pageable pageable);
	
	long countByBootGroup(BootGroup group);

	PostShare findByBootGroupAndBootUser(BootGroup group, BootUser user);
}
