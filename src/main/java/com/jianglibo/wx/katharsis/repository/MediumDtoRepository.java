package com.jianglibo.wx.katharsis.repository;

import com.jianglibo.wx.katharsis.dto.MediumDto;

import io.katharsis.queryspec.QuerySpec;
import io.katharsis.repository.ResourceRepositoryV2;
import io.katharsis.resource.list.ResourceListBase;

public interface MediumDtoRepository extends ResourceRepositoryV2<MediumDto, Long> {


	public class MediumDtoList extends ResourceListBase<MediumDto, DtoListMeta, DtoListLinks> {

	}

	@Override
	public MediumDtoList findAll(QuerySpec querySpec);
}

