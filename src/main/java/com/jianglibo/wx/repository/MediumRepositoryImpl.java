package com.jianglibo.wx.repository;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;

import com.jianglibo.wx.domain.Medium;

/**
 * To handle some conditions, not all conditions.
 * @author jianglibo@gmail.com
 *
 */
public class MediumRepositoryImpl extends SimpleJpaRepositoryBase<Medium> implements RepositoryBase<Medium> {

	@Autowired
	public MediumRepositoryImpl(EntityManager entityManager) {
		super(Medium.class, entityManager);
	}

}
