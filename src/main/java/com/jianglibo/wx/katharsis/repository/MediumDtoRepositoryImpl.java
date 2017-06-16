package com.jianglibo.wx.katharsis.repository;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jianglibo.wx.domain.Medium;
import com.jianglibo.wx.facade.MediumFacadeRepository;
import com.jianglibo.wx.katharsis.dto.MediumDto;
import com.jianglibo.wx.katharsis.dto.converter.MediumDtoConverter;
import com.jianglibo.wx.katharsis.repository.MediumDtoRepository.MediumDtoList;
import com.jianglibo.wx.util.QuerySpecUtil.RelationQuery;

import io.katharsis.queryspec.QuerySpec;

@Component
public class MediumDtoRepositoryImpl  extends DtoRepositoryBase<MediumDto, MediumDtoList, Medium, MediumFacadeRepository> implements MediumDtoRepository {
	
	@Autowired
	public MediumDtoRepositoryImpl(MediumFacadeRepository repository, MediumDtoConverter converter) {
		super(MediumDto.class, MediumDtoList.class, Medium.class, repository, converter);
	}

	@Override
	protected MediumDtoList findAllWithQuerySpec(QuerySpec querySpec) {
		return null;
	}

	@Override
	protected List<String> checkAllSortableFieldAllowed(QuerySpec querySpec) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected MediumDtoList findWithRelationAdnSpec(RelationQuery rq, QuerySpec querySpec) {
		// TODO Auto-generated method stub
		return null;
	}
}
