package com.jianglibo.wx.katharsis.repository;

import com.jianglibo.wx.katharsis.dto.GroupDto;

import io.katharsis.queryspec.QuerySpec;
import io.katharsis.repository.ResourceRepositoryV2;
import io.katharsis.resource.list.ResourceListBase;

public interface BootGroupDtoRepository extends ResourceRepositoryV2<GroupDto, Long> {


	public class BootGroupDtoList extends ResourceListBase<GroupDto, DtoListMeta, DtoListLinks> {

	}

	@Override
	public BootGroupDtoList findAll(QuerySpec querySpec);
}

