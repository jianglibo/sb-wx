package com.jianglibo.wx.katharsis.dto.converter;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jianglibo.wx.domain.LoginAttempt;
import com.jianglibo.wx.jwt.JwtUtil;
import com.jianglibo.wx.katharsis.dto.LoginAttemptDto;
import com.jianglibo.wx.vo.BootUserPrincipal;

@Component
public class LoginAttemptDtoConverter implements DtoConverter<LoginAttempt, LoginAttemptDto> {
	
	@Autowired
	private JwtUtil jwtUtil;

	@Override
	public LoginAttemptDto entity2Dto(LoginAttempt entity, Scenario scenario) {
		LoginAttemptDto dto = new LoginAttemptDto();
		BeanUtils.copyProperties(entity, dto);
		return dto;
	}
	
	public LoginAttemptDto newDto(LoginAttemptDto dto, BootUserPrincipal pricipal, LoginAttempt loginAttemp) {
		dto.setId(loginAttemp.getId());
		dto.setSuccess(true);
		dto.setPassword("");
		dto.setJwtToken(jwtUtil.issuePrincipalToken(pricipal));
		dto.setUser(pricipal.getId());
		return dto;
	}
}
