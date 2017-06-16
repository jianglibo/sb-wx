package com.jianglibo.wx.katharsis.repository;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jianglibo.wx.domain.Post;
import com.jianglibo.wx.facade.PostFacadeRepository;
import com.jianglibo.wx.katharsis.dto.PostDto;
import com.jianglibo.wx.katharsis.dto.converter.PostDtoConverter;
import com.jianglibo.wx.katharsis.repository.PostDtoRepository.PostDtoList;
import com.jianglibo.wx.util.QuerySpecUtil.RelationQuery;

import io.katharsis.queryspec.QuerySpec;

@Component
public class PostDtoRepositoryImpl  extends DtoRepositoryBase<PostDto, PostDtoList, Post, PostFacadeRepository> implements PostDtoRepository {
	
	@Autowired
	public PostDtoRepositoryImpl(PostFacadeRepository repository, PostDtoConverter converter) {
		super(PostDto.class, PostDtoList.class, Post.class, repository, converter);
	}

	@Override
	protected PostDtoList findAllWithQuerySpec(QuerySpec querySpec) {
		return null;
	}

	@Override
	protected List<String> checkAllSortableFieldAllowed(QuerySpec querySpec) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected PostDtoList findWithRelationAdnSpec(RelationQuery rq, QuerySpec querySpec) {
		// TODO Auto-generated method stub
		return null;
	}
}
