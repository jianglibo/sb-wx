package com.jianglibo.wx.facade.jpa;

import org.springframework.stereotype.Component;

import com.jianglibo.wx.domain.LoginAttempt;
import com.jianglibo.wx.facade.LoginAttemptFacadeRepository;
import com.jianglibo.wx.repository.LoginAttemptRepository;

@Component
public class LoginAttemptFacadeRepositoryImpl extends FacadeRepositoryBaseImpl<LoginAttempt, LoginAttemptRepository> implements  LoginAttemptFacadeRepository {

	public LoginAttemptFacadeRepositoryImpl(LoginAttemptRepository jpaRepo) {
		super(jpaRepo);
	}
}
