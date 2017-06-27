package com.jianglibo.wx.katharsis.repository;

import com.jianglibo.wx.katharsis.dto.GroupDto;

import io.katharsis.queryspec.QuerySpec;
import io.katharsis.repository.ResourceRepositoryV2;
import io.katharsis.resource.list.ResourceListBase;

public interface GroupDtoRepository extends ResourceRepositoryV2<GroupDto, Long> {


	public class GroupDtoList extends ResourceListBase<GroupDto, DtoListMeta, DtoListLinks> {

	}

	@Override
	public GroupDtoList findAll(QuerySpec querySpec);
}

