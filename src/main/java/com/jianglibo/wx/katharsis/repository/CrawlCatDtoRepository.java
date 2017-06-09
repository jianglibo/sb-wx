package com.jianglibo.wx.katharsis.repository;


import com.jianglibo.wx.katharsis.dto.CrawlCatDto;

import io.katharsis.queryspec.QuerySpec;
import io.katharsis.repository.ResourceRepositoryV2;
import io.katharsis.resource.list.ResourceListBase;

public interface CrawlCatDtoRepository extends ResourceRepositoryV2<CrawlCatDto, Long> {


	public class CrawlCatDtoList extends ResourceListBase<CrawlCatDto, DtoListMeta, DtoListLinks> {

	}

	@Override
	public CrawlCatDtoList findAll(QuerySpec querySpec);
}

