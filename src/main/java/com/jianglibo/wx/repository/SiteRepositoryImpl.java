package com.jianglibo.wx.repository;


import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;

import com.jianglibo.wx.domain.Site;

/**
 * @author jianglibo@gmail.com
 *
 */
public class SiteRepositoryImpl extends SimpleJpaRepositoryBase<Site> implements RepositoryBase<Site> {

    @Autowired
    public SiteRepositoryImpl(EntityManager entityManager) {
        super(Site.class, entityManager);
    }
    
    
}
