package com.jianglibo.wx.facade.jpa;

import com.jianglibo.wx.domain.BaseEntity;
import com.jianglibo.wx.facade.FacadeRepositoryBase;
import com.jianglibo.wx.facade.Page;
import com.jianglibo.wx.facade.PageFacade;
import com.jianglibo.wx.facade.SortBroker;
import com.jianglibo.wx.katharsis.dto.Dto;
import com.jianglibo.wx.katharsis.dto.DtoBase;
import com.jianglibo.wx.repository.RepositoryBase;

public abstract class FacadeRepositoryBaseImpl<T extends BaseEntity, D extends Dto, R extends RepositoryBase<T>> implements FacadeRepositoryBase<T, D> {

	private final R jpaRepo;
	
	public FacadeRepositoryBaseImpl(R jpaRepo) {
		this.jpaRepo = jpaRepo;
	}
	
	@Override
	public Page<T> findAll(PageFacade pf) {
		org.springframework.data.domain.Page<T> opage = jpaRepo.findAll(new SimplePageable(pf));
		return new Page<>(opage.getTotalElements(), opage.getContent());
	}
	
	protected SimplePageable getSimplePageable(long offset, long limit,SortBroker...sortFields) {
		return new SimplePageable(offset, limit, sortFields);
	}
	
	public T findOne(Long id) {
		return getRepository().findOne(id);
	}
	
	@Override
	public long count() {
		return jpaRepo.count();
	}

	@Override
	public T save(T entity, D dto) {
		return jpaRepo.save(entity);
	}

	@Override
	public void delete(T entity) {
		jpaRepo.delete(entity);
	}
	
	@Override
	public void delete(Long id) {
		jpaRepo.delete(id);
	}

	@Override
	public T findOne(Long id, boolean internalCall) {
		return jpaRepo.findOne(id);
	}
	
	R getRepository() {
		return jpaRepo;
	}

}
