package com.jianglibo.wx.repository;

import javax.persistence.EntityManager;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaEntityInformationSupport;

import com.jianglibo.wx.domain.LoginAttempt;

public class LoginAttemptRepositoryImpl extends SimpleJpaRepositoryBase<LoginAttempt>  implements  ApplicationContextAware, RepositoryBase<LoginAttempt> {

    @SuppressWarnings("unused")
    private EntityManager entityManager;

    @SuppressWarnings("unused")
    private ApplicationContext context;

    @SuppressWarnings("unused")
    private final JpaEntityInformation<LoginAttempt, ?> entityInformation;
    
    
    @Autowired
    public LoginAttemptRepositoryImpl(EntityManager entityManager) {
        super(LoginAttempt.class, entityManager);
        this.entityManager = entityManager;
        this.entityInformation = JpaEntityInformationSupport.getEntityInformation(LoginAttempt.class, entityManager);
    }
    
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

}
