package com.jianglibo.wx.repository;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;

import com.jianglibo.wx.domain.BootGroup;

/**
 * To handle some conditions, not all conditions.
 * @author jianglibo@gmail.com
 *
 */
public class BootGroupRepositoryImpl extends SimpleJpaRepositoryBase<BootGroup> implements RepositoryBase<BootGroup> {

	@Autowired
	public BootGroupRepositoryImpl(EntityManager entityManager) {
		super(BootGroup.class, entityManager);
	}

}
