package com.jianglibo.wx.katharsis.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import com.jianglibo.wx.domain.LoginAttempt;
import com.jianglibo.wx.facade.LoginAttemptFacadeRepository;
import com.jianglibo.wx.katharsis.dto.LoginAttemptDto;
import com.jianglibo.wx.katharsis.dto.converter.LoginAttemptDtoConverter;
import com.jianglibo.wx.katharsis.repository.LoginAttemptDtoRepository.LoginAttemptDtoList;
import com.jianglibo.wx.util.QuerySpecUtil.RelationQuery;
import com.jianglibo.wx.vo.BootUserPrincipal;

import io.katharsis.queryspec.QuerySpec;

@Component
@Transactional
public class LoginAttemptDtoRepositoryImpl  extends DtoRepositoryBase<LoginAttemptDto, LoginAttemptDtoList, LoginAttempt, LoginAttemptFacadeRepository> implements LoginAttemptDtoRepository {
	
	@Autowired
	private ApplicationContext applicationContext;
	
	private AuthenticationManager getAuthenticationManager() {
		return applicationContext.getBean(AuthenticationManager.class);
	}

	@Autowired
	public LoginAttemptDtoRepositoryImpl(LoginAttemptFacadeRepository repository, LoginAttemptDtoConverter converter) {
		super(LoginAttemptDto.class, LoginAttemptDtoList.class, LoginAttempt.class, repository, converter);
	}
	
	@Override
	public LoginAttemptDto createNew(LoginAttemptDto dto) {
		return invoke(dto);
	}
	
	@Override
	public LoginAttemptDto modify(LoginAttemptDto dto) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public void delete(Long id) {
		throw new UnsupportedOperationException();
	}
	
	@Transactional
	private LoginAttemptDto invoke(LoginAttemptDto dto) {
		dto.setJwtToken("");
		LoginAttempt loginAttemp = new LoginAttempt();
		loginAttemp.setProvider(dto.getProvider());
		loginAttemp.setUsername(dto.getUsername());
		
		try {
				UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(dto.getUsername(),
						dto.getPassword());
				Authentication an = getAuthenticationManager().authenticate(authRequest);
				BootUserPrincipal user = (BootUserPrincipal) an.getPrincipal();
				loginAttemp.setSuccess(true);
				loginAttemp.setPassword("");
				getRepository().save(loginAttemp);
				return ((LoginAttemptDtoConverter)getConverter()).newDto(dto, user, loginAttemp);
		} catch (AuthenticationException e) {
				getRepository().save(loginAttemp);
				throw e;
		}
	}

	@Override
	protected LoginAttemptDtoList findAllWithQuerySpec(QuerySpec querySpec) {
		return null;
	}

	@Override
	protected List<String> checkAllSortableFieldAllowed(QuerySpec querySpec) {
		return null;
	}

	@Override
	protected LoginAttemptDtoList findWithRelationAndSpec(RelationQuery rq, QuerySpec querySpec) {
		return null;
	}
	
}
