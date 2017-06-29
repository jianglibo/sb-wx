package com.jianglibo.wx.facade.jpa;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import com.jianglibo.wx.constant.PreAuthorizeExpression;
import com.jianglibo.wx.domain.BootGroup;
import com.jianglibo.wx.facade.BootGroupFacadeRepository;
import com.jianglibo.wx.katharsis.dto.GroupDto;
import com.jianglibo.wx.repository.BootGroupRepository;

/**
 * @author jianglibo@gmail.com
 *
 */
@Component
public class BootGroupFacadeRepositoryImpl extends FacadeRepositoryBaseImpl<BootGroup,GroupDto, BootGroupRepository> implements BootGroupFacadeRepository {
	
	public BootGroupFacadeRepositoryImpl(BootGroupRepository jpaRepo) {
		super(jpaRepo);
	}
	
	@Override
	@PreAuthorize(PreAuthorizeExpression.HAS_ADMINISTRATOR_ROLE)
	public BootGroup save(BootGroup entity, GroupDto dto) {
		return super.save(entity, dto);
	}
	

	@Override
	public BootGroup findByName(String rn) {
		return getRepository().findByName(rn);
	}

	@Override
	public BootGroup initSave(BootGroup entity) {
		return super.save(entity, null);
	}

	@Override
	public BootGroup newByDto(GroupDto dto) {
		BootGroup entity = new BootGroup(dto.getName());
		return entity;
	}
}
