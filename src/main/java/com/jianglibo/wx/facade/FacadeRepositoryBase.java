package com.jianglibo.wx.facade;

import java.util.List;

public interface FacadeRepositoryBase<E, D> {
	
	List<E> findRange(long offset, long limit, SortBroker...sortFields);
	
	long count();
	
	E save(E entity);
	
	void delete(Long id);
	
	E patch(E entity, D dto);
	
	E newByDto(D dto);

	E findOne(Long id, boolean internalCall);
	
	E findOne(Long id);

}
