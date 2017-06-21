package com.jianglibo.wx.katharsis.repository;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jianglibo.wx.domain.PostShare;
import com.jianglibo.wx.facade.PostShareFacadeRepository;
import com.jianglibo.wx.katharsis.dto.PostShareDto;
import com.jianglibo.wx.katharsis.dto.converter.PostShareDtoConverter;
import com.jianglibo.wx.katharsis.repository.PostShareDtoRepository.PostShareDtoList;
import com.jianglibo.wx.util.QuerySpecUtil.RelationQuery;

import io.katharsis.queryspec.QuerySpec;

@Component
public class PostShareDtoRepositoryImpl  extends DtoRepositoryBase<PostShareDto, PostShareDtoList, PostShare, PostShareFacadeRepository> implements PostShareDtoRepository {
	
	@Autowired
	public PostShareDtoRepositoryImpl(PostShareFacadeRepository repository, PostShareDtoConverter converter) {
		super(PostShareDto.class, PostShareDtoList.class, PostShare.class, repository, converter);
	}

	@Override
	protected PostShareDtoList findAllWithQuerySpec(QuerySpec querySpec) {
		return null;
	}

	@Override
	protected List<String> checkAllSortableFieldAllowed(QuerySpec querySpec) {
		return null;
	}

	@Override
	protected PostShareDtoList findWithRelationAdnSpec(RelationQuery rq, QuerySpec querySpec) {
		return null;
	}
}
