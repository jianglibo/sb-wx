package com.jianglibo.wx.repository;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;

import com.jianglibo.wx.domain.FollowRelation;

/**
 * To handle some conditions, not all conditions.
 * @author jianglibo@gmail.com
 *
 */
public class FollowRelationRepositoryImpl extends SimpleJpaRepositoryBase<FollowRelation> implements RepositoryBase<FollowRelation> {

	@Autowired
	public FollowRelationRepositoryImpl(EntityManager entityManager) {
		super(FollowRelation.class, entityManager);
	}

}
