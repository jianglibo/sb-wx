package com.jianglibo.wx.facade.jpa;

import java.util.List;

import com.jianglibo.wx.facade.FacadeRepositoryBase;
import com.jianglibo.wx.facade.SimplePageable;
import com.jianglibo.wx.facade.SortBroker;
import com.jianglibo.wx.repository.RepositoryBase;

public abstract class FacadeRepositoryBaseImpl<T, R extends RepositoryBase<T>> implements FacadeRepositoryBase<T> {

	private final R jpaRepo;
	
	public FacadeRepositoryBaseImpl(R jpaRepo) {
		this.jpaRepo = jpaRepo;
	}
	
	@Override
	public List<T> findRange(long offset, long limit,SortBroker...sortFields) {
		return jpaRepo.findAll(new SimplePageable(offset, limit, sortFields)).getContent();
	}
	
	protected SimplePageable getSimplePageable(long offset, long limit,SortBroker...sortFields) {
		return new SimplePageable(offset, limit, sortFields);
	}
	
	@Override
	public long count() {
		return jpaRepo.count();
	}

	@Override
	public T save(T entity) {
		return jpaRepo.save(entity);
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
