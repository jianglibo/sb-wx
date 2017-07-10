package com.jianglibo.wx.facade;

import com.jianglibo.wx.domain.BaseEntity;
import com.jianglibo.wx.katharsis.dto.Dto;

public interface FacadeRepositoryBase<E extends BaseEntity, D extends Dto> {
	
	Page<E> findAll(PageFacade pf);
	
	long count();
	
	E save(E entity, D dto);
	
	void delete(E entity);
	
//	void delete(Long id);
	
	E newByDto(D dto);

	E findOne(Long id, boolean internalCall);

}
