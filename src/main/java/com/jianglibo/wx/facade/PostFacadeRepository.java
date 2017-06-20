package com.jianglibo.wx.facade;


import java.util.List;

import com.jianglibo.wx.domain.Post;
import com.jianglibo.wx.katharsis.dto.PostDto;


public interface PostFacadeRepository extends FacadeRepositoryBase<Post, PostDto> {
	List<Post> findMine(long userId, long offset, Long limit, SortBroker...sortBrokers);
	long countMine(long userId, long offset, Long limit, SortBroker...sortBrokers);

}
