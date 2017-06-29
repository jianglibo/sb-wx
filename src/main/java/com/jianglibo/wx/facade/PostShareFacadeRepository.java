package com.jianglibo.wx.facade;

import com.jianglibo.wx.domain.BootUser;
import com.jianglibo.wx.domain.Post;
import com.jianglibo.wx.domain.PostShare;
import com.jianglibo.wx.katharsis.dto.PostShareDto;


public interface PostShareFacadeRepository extends FacadeRepositoryBase<PostShare, PostShareDto> {
	
	
	/**
	 * find by follower mean the follower is me. So the result is all I followed.
	 */
	Page<PostShare> findByBootUser(BootUser user, PageFacade pf);
	
	Page<PostShare> findByPost(Post post, PageFacade pf);

	PostShare findByPostAndBootUser(Post post, BootUser findOne);
	
}
