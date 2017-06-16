package com.jianglibo.wx.katharsis.dto.converter;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jianglibo.wx.domain.BootUser;
import com.jianglibo.wx.katharsis.dto.UserDto;

@Component
public class UserDtoConverter implements DtoConverter<BootUser, UserDto> {
	
	@Autowired
	private RoleDtoConverter roleConverter;

	@Override
	public BootUser dot2Entity(UserDto dto) {
		return null;
	}

	@Override
	public UserDto entity2Dto(BootUser entity) {
		// 18 fields.
		UserDto dto = new UserDto();
		dto.setAccountNonExpired(entity.isAccountNonExpired());
		dto.setAccountNonLocked(entity.isAccountNonLocked());
		dto.setAvatar(entity.getAvatar());
		dto.setCredentialsNonExpired(entity.isCredentialsNonExpired());
		dto.setDisplayName(entity.getDisplayName());
		dto.setEmail(entity.getEmail());
		dto.setEmailVerified(entity.isEmailVerified());
		dto.setEnabled(entity.isEnabled());
		dto.setGender(entity.getGender());
		dto.setId(entity.getId());
		dto.setMobile(entity.getMobile());
		dto.setMobileVerified(entity.isMobileVerified());
		dto.setName(entity.getName());
		dto.setPassword(null);
		dto.setCity(entity.getCity());
		dto.setCountry(entity.getCountry());
		dto.setLanguage(entity.getLanguage());
    	dto.setProvince(entity.getProvince());
    	dto.setRoles(entity.getRoles().stream().map(r -> roleConverter.entity2Dto(r)).collect(Collectors.toList()));
    	return dto;
	}

}
