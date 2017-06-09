package com.jianglibo.wx.facade.jpa;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import com.jianglibo.wx.constant.PreAuthorizeExpression;
import com.jianglibo.wx.domain.Role;
import com.jianglibo.wx.facade.RoleFacadeRepository;
import com.jianglibo.wx.repository.RoleRepository;

/**
 * @author jianglibo@gmail.com
 *
 */
@Component
public class RoleFacadeRepositoryImpl extends FacadeRepositoryBaseImpl<Role, RoleRepository> implements RoleFacadeRepository {
	
	public RoleFacadeRepositoryImpl(RoleRepository jpaRepo) {
		super(jpaRepo);
	}
	
	@Override
	@PreAuthorize(PreAuthorizeExpression.HAS_ADMINISTRATOR_ROLE)
	public Role save(Role entity) {
		return super.save(entity);
	}
	

	@Override
	public Role findByName(String rn) {
		return getRepository().findByName(rn);
	}

	@Override
	public Role initSave(Role entity) {
		return super.save(entity);
	}
}
