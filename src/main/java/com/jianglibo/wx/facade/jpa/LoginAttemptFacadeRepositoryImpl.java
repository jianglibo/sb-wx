package com.jianglibo.wx.facade.jpa;

import org.springframework.stereotype.Component;

import com.jianglibo.wx.domain.LoginAttempt;
import com.jianglibo.wx.facade.LoginAttemptFacadeRepository;
import com.jianglibo.wx.katharsis.dto.LoginAttemptDto;
import com.jianglibo.wx.repository.LoginAttemptRepository;

@Component
public class LoginAttemptFacadeRepositoryImpl extends FacadeRepositoryBaseImpl<LoginAttempt, LoginAttemptDto, LoginAttemptRepository> implements  LoginAttemptFacadeRepository {

	public LoginAttemptFacadeRepositoryImpl(LoginAttemptRepository jpaRepo) {
		super(jpaRepo);
	}

	@Override
	public LoginAttempt patch(LoginAttempt entity, LoginAttemptDto dto) {
		return null;
	}

	@Override
	public LoginAttempt newByDto(LoginAttemptDto dto) {
		return null;
	}
}
