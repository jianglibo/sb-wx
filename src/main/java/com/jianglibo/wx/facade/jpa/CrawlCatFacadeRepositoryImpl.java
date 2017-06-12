package com.jianglibo.wx.facade.jpa;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import com.jianglibo.wx.constant.PreAuthorizeExpression;
import com.jianglibo.wx.domain.CrawlCat;
import com.jianglibo.wx.facade.CrawlCatFacadeRepository;
import com.jianglibo.wx.facade.SortBroker;
import com.jianglibo.wx.repository.CrawlCatRepository;

/**
 * @author jianglibo@gmail.com
 *
 */
@Component
public class CrawlCatFacadeRepositoryImpl extends FacadeRepositoryBaseImpl<CrawlCat, CrawlCatRepository> implements CrawlCatFacadeRepository {

	@Autowired
	public CrawlCatFacadeRepositoryImpl(CrawlCatRepository jpaRepo) {
		super(jpaRepo);
	}
	
	@Override
	@PreAuthorize(PreAuthorizeExpression.HAS_ADMINISTRATOR_ROLE)
	public CrawlCat findOne(Long id, boolean internalCall) {
		return super.findOne(id, internalCall);
	}
	
	@Override
	@PreAuthorize(PreAuthorizeExpression.HAS_ADMINISTRATOR_ROLE)
	public List<CrawlCat> findRange(long offset, long limit, SortBroker... sortFields) {
		return super.findRange(offset, limit, sortFields);
	}

	@Override
	public CrawlCat findByName(String rn, boolean interalCall) {
		return getRepository().findByName(rn);
	}
	
	@Override
	@PreAuthorize(PreAuthorizeExpression.HAS_ADMINISTRATOR_ROLE)
	public CrawlCat save(CrawlCat entity) {
		return super.save(entity);
	}

}
