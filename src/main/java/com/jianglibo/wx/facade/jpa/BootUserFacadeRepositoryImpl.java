package com.jianglibo.wx.facade.jpa;


import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import com.jianglibo.wx.constant.PreAuthorizeExpression;
import com.jianglibo.wx.domain.BootGroup;
import com.jianglibo.wx.domain.BootUser;
import com.jianglibo.wx.facade.BootGroupFacadeRepository;
import com.jianglibo.wx.facade.BootUserFacadeRepository;
import com.jianglibo.wx.facade.SimplePageable;
import com.jianglibo.wx.facade.SortBroker;
import com.jianglibo.wx.katharsis.dto.UserDto;
import com.jianglibo.wx.repository.BootUserRepository;
import com.jianglibo.wx.util.SecurityUtil;
import com.jianglibo.wx.vo.RoleNames;

/**
 * @author jianglibo@gmail.com
 *
 */
@Component
public class BootUserFacadeRepositoryImpl extends FacadeRepositoryBaseImpl<BootUser, UserDto, BootUserRepository> implements BootUserFacadeRepository {
	

	@Autowired
	public BootUserFacadeRepositoryImpl(BootUserRepository jpaRepo) {
		super(jpaRepo);
	}
	
	@Autowired
	private BootGroupFacadeRepository groupRepo;
	
	@Override
    @PreAuthorize("hasRole('ADMINISTRATOR') and (#id != principal.id)")
	public void delete(@P("id") Long id) {
		super.delete(id);
	}


	@Override
	public BootUser findByEmail(String email) {
		return getRepository().findByEmail(email);
	}

	@Override
	public BootUser findByMobile(String mobile) {
		return getRepository().findByMobile(mobile);
	}

	@Override
	public BootUser findByName(String name) {
		return getRepository().findByName(name);
	}

	@Override
	@PreAuthorize(PreAuthorizeExpression.ID_EQUAL_OR_HAS_ADMINISTRATOR_ROLE)
	public BootUser updatePassword(@P("id") Long id, String encodedPassword) {
		BootUser entity = getRepository().findOne(id);
		entity.setPassword(encodedPassword);
		return getRepository().save(entity);
	}

	@Override
	@PreAuthorize("hasRole('ADMINISTRATOR') or (#user.id == principal.id)")
	public BootUser patch(@P("user") BootUser entity, UserDto dto) {
		if (SecurityUtil.hasRole(RoleNames.ROLE_ADMINISTRATOR)) {
			entity.setAccountNonExpired(dto.isAccountNonExpired());
			entity.setAccountNonLocked(dto.isAccountNonLocked());
			entity.setCredentialsNonExpired(dto.isCredentialsNonExpired());
			entity.setEmailVerified(dto.isEmailVerified());
			entity.setMobileVerified(dto.isMobileVerified());
			entity.setEnabled(dto.isEnabled());
		}
		setAllowChange(entity, dto);
		return getRepository().save(entity);
	}
	
	private void setAllowChange(BootUser entity, UserDto dto) {
		entity.setAvatar(dto.getAvatar());
		entity.setDisplayName(dto.getDisplayName());
		entity.setEmail(dto.getEmail());
		entity.setGender(dto.getGender());
		entity.setId(dto.getId());
		entity.setMobile(dto.getMobile());
		entity.setName(dto.getName());
		entity.setOpenId(dto.getOpenId());
		entity.setCity(dto.getCity());
		entity.setCountry(dto.getCountry());
		entity.setLanguage(dto.getLanguage());
	}

	@Override
	public BootUser findByOpenId(String openId) {
		return getRepository().findByOpenId(openId);
	}

	@Override
	public BootUser newByDto(UserDto dto) {
		return null;
	}

	@Override
	public List<BootUser> findAllByGroup(long groupId, long offset, Long limit, SortBroker... sortBrokers) {
		BootGroup bg = groupRepo.findOne(groupId);
		return getRepository().findAllByBootGroupsIn(Arrays.asList(bg), new SimplePageable(offset, limit, sortBrokers));
	}

	@Override
	public long countByGroup(long groupId, long offset, Long limit, SortBroker... sortBrokers) {
		BootGroup bg = groupRepo.findOne(groupId);
		return getRepository().countByBootGroupsIn(Arrays.asList(bg));
	}
}
