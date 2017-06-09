package com.jianglibo.wx.katharsis.repository;

import org.springframework.stereotype.Component;

import com.jianglibo.wx.katharsis.alter.RelationshipRepositoryBaseMy;
import com.jianglibo.wx.katharsis.dto.CrawlCatDto;
import com.jianglibo.wx.katharsis.dto.SiteDto;

@Component
public class CrawlCatToSiteRepositoryImpl extends RelationshipRepositoryBaseMy<CrawlCatDto, SiteDto> {

    protected CrawlCatToSiteRepositoryImpl() {
		super(CrawlCatDto.class, SiteDto.class);
	}
}
