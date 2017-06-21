package com.jianglibo.wx.facade;

import java.util.List;

import com.jianglibo.wx.domain.BootUser;
import com.jianglibo.wx.domain.Post;
import com.jianglibo.wx.domain.PostShare;
import com.jianglibo.wx.katharsis.dto.PostShareDto;


public interface PostShareFacadeRepository extends FacadeRepositoryBase<PostShare, PostShareDto> {
	
	
	/**
	 * find by follower mean the follower is me. So the result is all I followed.
	 */
	List<PostShare> findByBootUser(BootUser user, long offset, Long limit, SortBroker...sortBrokers);
	
	long countByBootUser(BootUser user);
	
	List<PostShare> findByPost(Post post, long offset, Long limit, SortBroker...sortBrokers);
	
	long countByPost(Post post);

	PostShare findByPostAndBootUser(Post post, BootUser findOne);
	
	
}
