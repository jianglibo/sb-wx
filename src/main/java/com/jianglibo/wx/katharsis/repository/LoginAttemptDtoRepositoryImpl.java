package com.jianglibo.wx.katharsis.repository;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import com.jianglibo.wx.domain.BootUser;
import com.jianglibo.wx.domain.LoginAttempt;
import com.jianglibo.wx.facade.BootUserFacadeRepository;
import com.jianglibo.wx.facade.LoginAttemptFacadeRepository;
import com.jianglibo.wx.jwt.JwtUtil;
import com.jianglibo.wx.katharsis.dto.LoginAttemptDto;
import com.jianglibo.wx.katharsis.dto.RoleDto;
import com.jianglibo.wx.katharsis.dto.UserDto;
import com.jianglibo.wx.katharsis.repository.LoginAttemptDtoRepository.LoginAttemptDtoList;
import com.jianglibo.wx.util.QuerySpecUtil.RelationQuery;
import com.jianglibo.wx.vo.BootUserPrincipal;

import io.katharsis.queryspec.QuerySpec;

@Component
@Transactional
public class LoginAttemptDtoRepositoryImpl  extends DtoRepositoryBase<LoginAttemptDto, LoginAttemptDtoList, LoginAttempt, LoginAttemptFacadeRepository> implements LoginAttemptDtoRepository {
	
	@Autowired
	private ApplicationContext applicationContext;
	
	@Autowired
	private BootUserFacadeRepository userRepository;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	private AuthenticationManager getAuthenticationManager() {
		return applicationContext.getBean(AuthenticationManager.class);
	}

	@Autowired
	public LoginAttemptDtoRepositoryImpl(LoginAttemptFacadeRepository repository) {
		super(LoginAttemptDto.class, LoginAttemptDtoList.class, LoginAttempt.class, repository);
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
				dto.setId(loginAttemp.getId());
				dto.setSuccess(true);
				dto.setPassword("");
				dto.setJwtToken(jwtUtil.issuePrincipalToken(user));
				BootUser bu = userRepository.findOne(user.getId(), true);
				UserDto udto = new UserDto().fromEntity(bu);
				udto.setRoles(bu.getRoles().stream().map(r -> new RoleDto().fromEntity(r)).collect(Collectors.toList()));
				dto.setUser(udto);
				return dto;
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
	protected LoginAttemptDtoList findWithRelationAdnSpec(RelationQuery rq, QuerySpec querySpec) {
		return null;
	}
	
}
