package com.jianglibo.wx.facade;

import com.jianglibo.wx.domain.CrawlCat;

public interface CrawlCatFacadeRepository extends FacadeRepositoryBase<CrawlCat> {
	
	CrawlCat findByName(String rn, boolean internalCall);
}
