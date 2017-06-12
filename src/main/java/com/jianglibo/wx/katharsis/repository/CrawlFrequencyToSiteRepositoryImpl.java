package com.jianglibo.wx.katharsis.repository;

import org.springframework.stereotype.Component;

import com.jianglibo.wx.katharsis.dto.CrawlFrequencyDto;
import com.jianglibo.wx.katharsis.dto.SiteDto;

import io.katharsis.repository.RelationshipRepositoryBase;

@Component
public class CrawlFrequencyToSiteRepositoryImpl extends RelationshipRepositoryBase<CrawlFrequencyDto, Long, SiteDto, Long> {

    protected CrawlFrequencyToSiteRepositoryImpl() {
		super(CrawlFrequencyDto.class, SiteDto.class);
	}
}
