package com.jianglibo.wx.facade;

import java.util.List;

import com.jianglibo.wx.domain.Site;

public interface SiteFacadeRepository extends FacadeRepositoryBase<Site> {
	
	Site findByDomainName(String homepage, boolean internalCall);
	
	List<Site> findByCrawlCat(Long crawlCatId, long offset, Long limit, SortBroker...sortBrokers);
	
	long countByCrawlCat(Long crawlCatId);
}
