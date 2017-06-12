package com.jianglibo.wx.facade.jpa;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import com.jianglibo.wx.constant.PreAuthorizeExpression;
import com.jianglibo.wx.domain.BootUser;
import com.jianglibo.wx.facade.BootUserFacadeRepository;
import com.jianglibo.wx.katharsis.dto.UserDto;
import com.jianglibo.wx.repository.BootUserRepository;
import com.jianglibo.wx.util.SecurityUtil;
import com.jianglibo.wx.vo.RoleNames;

/**
 * @author jianglibo@gmail.com
 *
 */
@Component
public class BootUserFacadeRepositoryImpl extends FacadeRepositoryBaseImpl<BootUser, BootUserRepository> implements BootUserFacadeRepository {
	

	@Autowired
	public BootUserFacadeRepositoryImpl(BootUserRepository jpaRepo) {
		super(jpaRepo);
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
	@PreAuthorize(PreAuthorizeExpression.ID_EQUAL_OR_HAS_ADMINISTRATOR_ROLE)
	public BootUser updatePassword(@P("id") Long id, String encodedPassword) {
		BootUser entity = getRepository().findOne(id);
		entity.setPassword(encodedPassword);
		return getRepository().save(entity);
	}

	@Override
	@PreAuthorize("hasRole('ADMINISTRATOR') or (#user.id == principal.id)")
	public BootUser patch(@P("user") BootUser user, UserDto userDto) {
		if (SecurityUtil.hasRole(RoleNames.ROLE_ADMINISTRATOR)) {
			userDto.patch(user);
		} else {
			userDto.patchLeaveStatusUnChanged(user);
		}
		return getRepository().save(user);
	}
}
