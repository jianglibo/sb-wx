package com.jianglibo.wx.katharsis.repository;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jianglibo.wx.domain.CrawlFrequency;
import com.jianglibo.wx.facade.CrawlFrequencyFacadeRepository;
import com.jianglibo.wx.facade.SiteFacadeRepository;
import com.jianglibo.wx.katharsis.dto.CrawlFrequencyDto;
import com.jianglibo.wx.katharsis.dto.SiteDto;
import com.jianglibo.wx.katharsis.repository.CrawlFrequencyDtoRepository.CrawlFrequencyDtoList;
import com.jianglibo.wx.util.QuerySpecUtil.RelationQuery;

import io.katharsis.queryspec.QuerySpec;


@Component
public class CrawlFrequencyDtoRepositoryImpl  extends DtoRepositoryBase<CrawlFrequencyDto, CrawlFrequencyDtoList, CrawlFrequency, CrawlFrequencyFacadeRepository> implements CrawlFrequencyDtoRepository {
	
	private final SiteFacadeRepository siteRepository;
	
	@Autowired
	public CrawlFrequencyDtoRepositoryImpl(CrawlFrequencyFacadeRepository repository, SiteFacadeRepository siteRepository) {
		super(CrawlFrequencyDto.class, CrawlFrequencyDtoList.class, CrawlFrequency.class, repository);
		this.siteRepository = siteRepository;
	}
	
	@Override
	public CrawlFrequency saveToBackendRepo(CrawlFrequencyDto dto, CrawlFrequency entity) {
		SiteDto sd = dto.getSite();
		entity.setSite(siteRepository.findOne(sd.getId(), true));
		return getRepository().save(entity);
	}

	@Override
	protected CrawlFrequencyDtoList findAllWithQuerySpec(QuerySpec querySpec) {
		return null;
	}

	@Override
	protected List<String> checkAllSortableFieldAllowed(QuerySpec querySpec) {
		return null;
	}

	@Override
	protected CrawlFrequencyDtoList findWithRelationAdnSpec(RelationQuery rq, QuerySpec querySpec) {
		return null;
	}
}
