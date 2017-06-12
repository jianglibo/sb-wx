package com.jianglibo.wx.repository;

import com.jianglibo.wx.domain.CrawlCat;

public interface CrawlCatRepository extends RepositoryBase<CrawlCat> {
	CrawlCat findByName(String rn);
}
