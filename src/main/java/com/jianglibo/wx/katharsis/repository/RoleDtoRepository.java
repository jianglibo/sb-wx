package com.jianglibo.wx.katharsis.repository;

import com.jianglibo.wx.katharsis.dto.RoleDto;

import io.katharsis.queryspec.QuerySpec;
import io.katharsis.repository.ResourceRepositoryV2;
import io.katharsis.resource.list.ResourceListBase;

public interface RoleDtoRepository extends ResourceRepositoryV2<RoleDto, Long> {


	public class RoleDtoList extends ResourceListBase<RoleDto, DtoListMeta, DtoListLinks> {

	}

	@Override
	public RoleDtoList findAll(QuerySpec querySpec);
}

