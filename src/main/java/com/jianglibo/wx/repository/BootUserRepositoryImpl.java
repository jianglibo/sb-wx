package com.jianglibo.wx.repository;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;

import com.jianglibo.wx.domain.BootUser;

/**
 * @author jianglibo@gmail.com
 *
 */
public class BootUserRepositoryImpl extends SimpleJpaRepositoryBase<BootUser> implements RepositoryBase<BootUser>{

    @Autowired
    public BootUserRepositoryImpl(EntityManager entityManager) {
        super(BootUser.class, entityManager);
    }
}
