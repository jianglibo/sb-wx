package com.jianglibo.wx.facade.jpa;

import java.util.List;

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
	public BootGroup save(BootGroup entity) {
		return super.save(entity);
	}
	

	@Override
	public BootGroup findByName(String rn) {
		return getRepository().findByName(rn);
	}

	@Override
	public BootGroup initSave(BootGroup entity) {
		return super.save(entity);
	}

	@Override
	public List<BootGroup> findAll() {
		return getRepository().findAll();
	}

	@Override
	public BootGroup patch(BootGroup entity, GroupDto dto) {
		entity.setName(dto.getName());
		return entity;
	}

	@Override
	public BootGroup newByDto(GroupDto dto) {
		BootGroup entity = new BootGroup(dto.getName());
		return entity;
	}
}
