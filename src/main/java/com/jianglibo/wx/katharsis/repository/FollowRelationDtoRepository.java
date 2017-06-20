package com.jianglibo.wx.katharsis.repository;

import com.jianglibo.wx.katharsis.dto.FollowRelationDto;

import io.katharsis.queryspec.QuerySpec;
import io.katharsis.repository.ResourceRepositoryV2;
import io.katharsis.resource.list.ResourceListBase;

public interface FollowRelationDtoRepository extends ResourceRepositoryV2<FollowRelationDto, Long> {


	public class FollowRelationDtoList extends ResourceListBase<FollowRelationDto, DtoListMeta, DtoListLinks> {

	}

	@Override
	public FollowRelationDtoList findAll(QuerySpec querySpec);
}

