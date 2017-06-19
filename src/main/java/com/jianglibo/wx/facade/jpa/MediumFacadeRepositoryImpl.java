package com.jianglibo.wx.facade.jpa;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import com.jianglibo.wx.constant.PreAuthorizeExpression;
import com.jianglibo.wx.domain.Medium;
import com.jianglibo.wx.facade.MediumFacadeRepository;
import com.jianglibo.wx.katharsis.dto.MediumDto;
import com.jianglibo.wx.repository.MediumRepository;

/**
 * @author jianglibo@gmail.com
 *
 */
@Component
public class MediumFacadeRepositoryImpl extends FacadeRepositoryBaseImpl<Medium,MediumDto, MediumRepository> implements MediumFacadeRepository {
	
	public MediumFacadeRepositoryImpl(MediumRepository jpaRepo) {
		super(jpaRepo);
	}
	
	@Override
	@PreAuthorize(PreAuthorizeExpression.IS_FULLY_AUTHENTICATED)
	public Medium save(Medium entity) {
		return super.save(entity);
	}

	@Override
	public Medium patch(Medium entity, MediumDto dto) {
//		entity.setName(dto.getName());
		return entity;
	}

	@Override
	public Medium newByDto(MediumDto dto) {
		Medium entity = new Medium();
		return entity;
	}
}
