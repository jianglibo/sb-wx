package com.jianglibo.wx.katharsis.repository;

import org.springframework.stereotype.Component;

import com.jianglibo.wx.katharsis.alter.RelationshipRepositoryBaseMy;
import com.jianglibo.wx.katharsis.dto.CrawlCatDto;
import com.jianglibo.wx.katharsis.dto.SiteDto;

@Component
public class SiteToCrawlCatRepository extends RelationshipRepositoryBaseMy<SiteDto, CrawlCatDto> {

	protected SiteToCrawlCatRepository() {
		super(SiteDto.class, CrawlCatDto.class);
	}
}
