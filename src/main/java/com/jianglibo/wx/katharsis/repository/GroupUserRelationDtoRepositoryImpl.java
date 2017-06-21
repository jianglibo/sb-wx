package com.jianglibo.wx.katharsis.repository;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jianglibo.wx.domain.GroupUserRelation;
import com.jianglibo.wx.facade.GroupUserRelationFacadeRepository;
import com.jianglibo.wx.katharsis.dto.GroupUserRelationDto;
import com.jianglibo.wx.katharsis.dto.converter.GroupUserRelationDtoConverter;
import com.jianglibo.wx.katharsis.repository.GroupUserRelationDtoRepository.GroupUserRelationDtoList;
import com.jianglibo.wx.util.QuerySpecUtil.RelationQuery;

import io.katharsis.queryspec.QuerySpec;

@Component
public class GroupUserRelationDtoRepositoryImpl  extends DtoRepositoryBase<GroupUserRelationDto, GroupUserRelationDtoList, GroupUserRelation, GroupUserRelationFacadeRepository> implements GroupUserRelationDtoRepository {
	
	@Autowired
	public GroupUserRelationDtoRepositoryImpl(GroupUserRelationFacadeRepository repository, GroupUserRelationDtoConverter converter) {
		super(GroupUserRelationDto.class, GroupUserRelationDtoList.class, GroupUserRelation.class, repository, converter);
	}

	@Override
	protected GroupUserRelationDtoList findAllWithQuerySpec(QuerySpec querySpec) {
		return null;
	}

	@Override
	protected List<String> checkAllSortableFieldAllowed(QuerySpec querySpec) {
		return null;
	}

	@Override
	protected GroupUserRelationDtoList findWithRelationAndSpec(RelationQuery rq, QuerySpec querySpec) {
		return null;
	}
}
