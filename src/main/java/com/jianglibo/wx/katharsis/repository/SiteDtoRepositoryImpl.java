package com.jianglibo.wx.katharsis.repository;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jianglibo.wx.domain.Site;
import com.jianglibo.wx.facade.CrawlCatFacadeRepository;
import com.jianglibo.wx.facade.SiteFacadeRepository;
import com.jianglibo.wx.katharsis.dto.CrawlCatDto;
import com.jianglibo.wx.katharsis.dto.SiteDto;
import com.jianglibo.wx.katharsis.repository.SiteDtoRepository.SiteDtoList;
import com.jianglibo.wx.util.QuerySpecUtil;
import com.jianglibo.wx.util.QuerySpecUtil.RelationQuery;

import io.katharsis.queryspec.QuerySpec;

@Component
public class SiteDtoRepositoryImpl  extends DtoRepositoryBase<SiteDto, SiteDtoList, Site, SiteFacadeRepository> implements SiteDtoRepository {
	
	private final CrawlCatFacadeRepository ccrepository;
	
	@Autowired
	public SiteDtoRepositoryImpl(SiteFacadeRepository repository, CrawlCatFacadeRepository ccrepository) {
		super(SiteDto.class, SiteDtoList.class, Site.class, repository);
		this.ccrepository = ccrepository;
	}

	@Override
	public Site saveToBackendRepo(SiteDto dto, Site entity) {
		entity.setCrawlCat(ccrepository.findOne(dto.getCrawlCat().getId(), true));
		return getRepository().save(entity);
	}
	
	@Override
	public SiteDto convertToDto(Site site) {
		SiteDto siteDto = super.convertToDto(site);
		siteDto.setCrawlCat(new CrawlCatDto().fromEntity(site.getCrawlCat()));
		return siteDto;
	}

	@Override
	protected SiteDtoList findAllWithQuerySpec(QuerySpec querySpec) {
		return null;
	}

	@Override
	protected List<String> checkAllSortableFieldAllowed(QuerySpec querySpec) {
		return null;
	}

	@Override
	protected SiteDtoList findWithRelationAdnSpec(RelationQuery rq, QuerySpec querySpec) {
		if ("crawlCat".equals(rq.getRelationName())) {
			List<Site> sites = getRepository().findByCrawlCat(rq.getRelationIds().get(0), querySpec.getOffset(), querySpec.getLimit(), QuerySpecUtil.getSortBrokers(querySpec));
			long count = getRepository().countByCrawlCat(rq.getRelationIds().get(0));
			return convertToResourceList(sites, count);
		}
		return null;
	}

}
