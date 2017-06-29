package com.jianglibo.wx.facade.jpa;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import com.jianglibo.wx.constant.PreAuthorizeExpression;
import com.jianglibo.wx.domain.Role;
import com.jianglibo.wx.facade.RoleFacadeRepository;
import com.jianglibo.wx.katharsis.dto.RoleDto;
import com.jianglibo.wx.repository.RoleRepository;

/**
 * @author jianglibo@gmail.com
 *
 */
@Component
public class RoleFacadeRepositoryImpl extends FacadeRepositoryBaseImpl<Role,RoleDto, RoleRepository> implements RoleFacadeRepository {
	
	public RoleFacadeRepositoryImpl(RoleRepository jpaRepo) {
		super(jpaRepo);
	}
	
	@Override
	@PreAuthorize(PreAuthorizeExpression.HAS_ADMINISTRATOR_ROLE)
	public Role save(Role entity, RoleDto dto) {
		return super.save(entity, dto);
	}
	

	@Override
	public Role findByName(String rn) {
		if (!(rn.startsWith("role_") || rn.startsWith("ROLE_"))) {
			rn = ("role_" + rn).toUpperCase();
		}
		return getRepository().findByName(rn);
	}

	@Override
	public Role initSave(Role entity) {
		return super.save(entity, null);
	}

	@Override
	public List<Role> findAll() {
		return getRepository().findAll();
	}

	@Override
	public Role newByDto(RoleDto dto) {
		Role entity = new Role(dto.getName());
		return entity;
	}
}
