package com.jianglibo.wx.facade;

import java.util.List;

import com.jianglibo.wx.domain.MySite;

public interface MySiteFacadeRepository extends FacadeRepositoryBase<MySite> {

	List<MySite> findMine(long userId, long offset, Long limit, SortBroker...sortBrokers);

	long countMine(long userId, long offset, Long limit, SortBroker...sortBrokers);
	
}
