package com.jianglibo.wx.repository;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;

import com.jianglibo.wx.domain.MessageNotify;

/**
 * To handle some conditions, not all conditions.
 * @author jianglibo@gmail.com
 *
 */
public class MessageNotifyRepositoryImpl extends SimpleJpaRepositoryBase<MessageNotify> implements RepositoryBase<MessageNotify> {

	@Autowired
	public MessageNotifyRepositoryImpl(EntityManager entityManager) {
		super(MessageNotify.class, entityManager);
	}

}
