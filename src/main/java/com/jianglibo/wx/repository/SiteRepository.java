package com.jianglibo.wx.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.jianglibo.wx.domain.CrawlCat;
import com.jianglibo.wx.domain.Site;

public interface SiteRepository extends RepositoryBase<Site> {

	Site findByDomainName(String homepage);
	
	List<Site> findAllByOrderByUpdatedAtDesc();
	
	List<Site> findByCrawlCat(CrawlCat crawlCat, Pageable pageable);
	
	long countByCrawlCat(CrawlCat crawlCat);
}
