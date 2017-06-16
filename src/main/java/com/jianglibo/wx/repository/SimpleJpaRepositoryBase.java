package com.jianglibo.wx.repository;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

public abstract class SimpleJpaRepositoryBase<T> extends SimpleJpaRepository<T, Long> implements RepositoryBase<T>{
	
	public SimpleJpaRepositoryBase(Class<T> domainClass, EntityManager em) {
		super(domainClass, em);
	}
}
