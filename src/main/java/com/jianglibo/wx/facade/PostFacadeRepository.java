package com.jianglibo.wx.facade;


import com.jianglibo.wx.domain.BootUser;
import com.jianglibo.wx.domain.Post;
import com.jianglibo.wx.katharsis.dto.PostDto;


public interface PostFacadeRepository extends FacadeRepositoryBase<Post, PostDto> {
	Page<Post> findMine(BootUser user, PageFacade pf);
	void saveSharePost(final Post fe, BootUser user);
	Page<Post> findAllByToAll(boolean toAll, PageFacade pageFacade);
}
