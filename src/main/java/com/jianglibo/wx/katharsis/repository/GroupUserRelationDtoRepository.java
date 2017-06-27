package com.jianglibo.wx.katharsis.repository;

import java.util.List;

import com.jianglibo.wx.domain.BootUser;
import com.jianglibo.wx.domain.GroupUserRelation;
import com.jianglibo.wx.facade.SortBroker;
import com.jianglibo.wx.katharsis.dto.GroupUserRelationDto;

import io.katharsis.queryspec.QuerySpec;
import io.katharsis.repository.ResourceRepositoryV2;
import io.katharsis.resource.list.ResourceListBase;

public interface GroupUserRelationDtoRepository extends ResourceRepositoryV2<GroupUserRelationDto, Long> {


	public class GroupUserRelationDtoList extends ResourceListBase<GroupUserRelationDto, DtoListMeta, DtoListLinks> {
	}

	@Override
	public GroupUserRelationDtoList findAll(QuerySpec querySpec);

	public List<GroupUserRelation> findByUser(BootUser bu, long offset, Long limit, SortBroker...sortBrokers);

	public long countByUser(BootUser bu);
}

