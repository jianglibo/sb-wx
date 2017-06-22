package com.jianglibo.wx.katharsis.repository;

import com.jianglibo.wx.katharsis.dto.ApproveDto;

import io.katharsis.queryspec.QuerySpec;
import io.katharsis.repository.ResourceRepositoryV2;
import io.katharsis.resource.list.ResourceListBase;

public interface ApproveDtoRepository extends ResourceRepositoryV2<ApproveDto, Long> {


	public class ApproveDtoList extends ResourceListBase<ApproveDto, DtoListMeta, DtoListLinks> {

	}

	@Override
	public ApproveDtoList findAll(QuerySpec querySpec);
}

