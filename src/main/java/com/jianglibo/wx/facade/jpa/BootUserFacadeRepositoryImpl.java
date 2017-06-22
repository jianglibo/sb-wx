package com.jianglibo.wx.facade.jpa;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import com.jianglibo.wx.constant.PreAuthorizeExpression;
import com.jianglibo.wx.domain.BootUser;
import com.jianglibo.wx.facade.BootUserFacadeRepository;
import com.jianglibo.wx.facade.GroupUserRelationFacadeRepository;
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
	private GroupUserRelationFacadeRepository gurRepo;

	@Autowired
	public BootUserFacadeRepositoryImpl(BootUserRepository jpaRepo) {
		super(jpaRepo);
	}
	
	@Override
    @PreAuthorize("hasRole('ADMINISTRATOR') and (#entity.id != principal.id)")
	public void delete(@P("entity") BootUser entity) {
		super.delete(entity);
	}
	
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
	@PreAuthorize(PreAuthorizeExpression.ENTITY_ID_EQUAL_OR_HAS_ADMINISTRATOR_ROLE)
	public BootUser updatePassword(@P("entity") BootUser user, String encodedPassword) {
		user.setPassword(encodedPassword);
		return getRepository().save(user);
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
		return gurRepo.findByBootGroup(groupId,offset,limit,sortBrokers).stream().map(gur -> gur.getBootUser()).collect(Collectors.toList());
	}

	@Override
	public long countByGroup(long groupId) {
		return gurRepo.countByBootGroup(groupId);
	}

}
