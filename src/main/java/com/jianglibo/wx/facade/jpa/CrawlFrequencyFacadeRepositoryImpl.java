package com.jianglibo.wx.facade.jpa;

import org.springframework.stereotype.Component;

import com.jianglibo.wx.domain.CrawlFrequency;
import com.jianglibo.wx.facade.CrawlFrequencyFacadeRepository;
import com.jianglibo.wx.repository.CrawlFrequencyRepository;

/**
 * @author jianglibo@gmail.com
 *
 */
@Component
public class CrawlFrequencyFacadeRepositoryImpl extends FacadeRepositoryBaseImpl<CrawlFrequency, CrawlFrequencyRepository> implements CrawlFrequencyFacadeRepository {

	public CrawlFrequencyFacadeRepositoryImpl(CrawlFrequencyRepository jpaRepo) {
		super(jpaRepo);
	}
}
