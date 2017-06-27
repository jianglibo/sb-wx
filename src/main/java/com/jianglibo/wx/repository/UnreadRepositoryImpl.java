package com.jianglibo.wx.repository;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;

import com.jianglibo.wx.domain.Unread;

/**
 * To handle some conditions, not all conditions.
 * @author jianglibo@gmail.com
 *
 */
public class UnreadRepositoryImpl extends SimpleJpaRepositoryBase<Unread> implements RepositoryBase<Unread> {

	@Autowired
	public UnreadRepositoryImpl(EntityManager entityManager) {
		super(Unread.class, entityManager);
	}

}
