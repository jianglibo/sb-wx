package com.jianglibo.wx.katharsis.dto.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jianglibo.wx.domain.BootUser;
import com.jianglibo.wx.domain.LoginAttempt;
import com.jianglibo.wx.facade.BootUserFacadeRepository;
import com.jianglibo.wx.jwt.JwtUtil;
import com.jianglibo.wx.katharsis.dto.LoginAttemptDto;
import com.jianglibo.wx.katharsis.dto.UserDto;
import com.jianglibo.wx.vo.BootUserPrincipal;

@Component
public class LoginAttemptDtoConverter implements DtoConverter<LoginAttempt, LoginAttemptDto> {
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private BootUserFacadeRepository userRepository;
	
	@Autowired
	private UserDtoConverter userConverter;

	@Override
	public LoginAttempt dot2Entity(LoginAttemptDto dto) {
		return null;
	}

	@Override
	public LoginAttemptDto entity2Dto(LoginAttempt entity) {
		LoginAttemptDto dto = new LoginAttemptDto();
		dto.setId(entity.getId());
		dto.setPassword(entity.getPassword());
		dto.setProvider(entity.getProvider());
		dto.setRemoteAddress(entity.getRemoteAddress());
		dto.setSessionId(entity.getSessionId());
		dto.setSuccess(entity.isSuccess());
		dto.setUsername(entity.getUsername());
		return dto;
	}
	
	public LoginAttemptDto newDto(LoginAttemptDto dto, BootUserPrincipal pricipal, LoginAttempt loginAttemp) {
		dto.setId(loginAttemp.getId());
		dto.setSuccess(true);
		dto.setPassword("");
		dto.setJwtToken(jwtUtil.issuePrincipalToken(pricipal));
		BootUser bu = userRepository.findOne(pricipal.getId(), true);
		UserDto udto = userConverter.entity2Dto(bu);
		dto.setUser(udto);
		return dto;
	}
}
