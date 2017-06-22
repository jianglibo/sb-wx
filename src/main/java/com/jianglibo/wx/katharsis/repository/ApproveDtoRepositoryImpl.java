package com.jianglibo.wx.katharsis.repository;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jianglibo.wx.domain.Approve;
import com.jianglibo.wx.facade.ApproveFacadeRepository;
import com.jianglibo.wx.katharsis.dto.ApproveDto;
import com.jianglibo.wx.katharsis.dto.converter.ApproveDtoConverter;
import com.jianglibo.wx.katharsis.repository.ApproveDtoRepository.ApproveDtoList;
import com.jianglibo.wx.util.QuerySpecUtil.RelationQuery;

import io.katharsis.queryspec.QuerySpec;

@Component
public class ApproveDtoRepositoryImpl  extends DtoRepositoryBase<ApproveDto, ApproveDtoList, Approve, ApproveFacadeRepository> implements ApproveDtoRepository {
	
	@Autowired
	public ApproveDtoRepositoryImpl(ApproveFacadeRepository repository, ApproveDtoConverter converter) {
		super(ApproveDto.class, ApproveDtoList.class, Approve.class, repository, converter);
	}

	@Override
	protected ApproveDtoList findAllWithQuerySpec(QuerySpec querySpec) {
		return null;
	}

	@Override
	protected List<String> checkAllSortableFieldAllowed(QuerySpec querySpec) {
		return null;
	}

	@Override
	protected ApproveDtoList findWithRelationAndSpec(RelationQuery rq, QuerySpec querySpec) {
		return null;
	}
}
