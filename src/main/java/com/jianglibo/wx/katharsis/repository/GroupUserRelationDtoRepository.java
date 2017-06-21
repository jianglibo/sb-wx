package com.jianglibo.wx.katharsis.repository;

import com.jianglibo.wx.katharsis.dto.GroupUserRelationDto;

import io.katharsis.queryspec.QuerySpec;
import io.katharsis.repository.ResourceRepositoryV2;
import io.katharsis.resource.list.ResourceListBase;

public interface GroupUserRelationDtoRepository extends ResourceRepositoryV2<GroupUserRelationDto, Long> {


	public class GroupUserRelationDtoList extends ResourceListBase<GroupUserRelationDto, DtoListMeta, DtoListLinks> {

	}

	@Override
	public GroupUserRelationDtoList findAll(QuerySpec querySpec);
}

