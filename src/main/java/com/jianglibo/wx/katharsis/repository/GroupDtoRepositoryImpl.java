package com.jianglibo.wx.katharsis.repository;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jianglibo.wx.domain.BootGroup;
import com.jianglibo.wx.facade.BootGroupFacadeRepository;
import com.jianglibo.wx.katharsis.dto.GroupDto;
import com.jianglibo.wx.katharsis.dto.converter.GroupDtoConverter;
import com.jianglibo.wx.katharsis.repository.GroupDtoRepository.BootGroupDtoList;
import com.jianglibo.wx.util.QuerySpecUtil.RelationQuery;

import io.katharsis.queryspec.QuerySpec;

@Component
public class GroupDtoRepositoryImpl  extends DtoRepositoryBase<GroupDto, BootGroupDtoList, BootGroup, BootGroupFacadeRepository> implements GroupDtoRepository {
	
	@Autowired
	public GroupDtoRepositoryImpl(BootGroupFacadeRepository repository, GroupDtoConverter converter) {
		super(GroupDto.class, BootGroupDtoList.class, BootGroup.class, repository, converter);
	}

	@Override
	protected BootGroupDtoList findAllWithQuerySpec(QuerySpec querySpec) {
		return null;
	}

	@Override
	protected List<String> checkAllSortableFieldAllowed(QuerySpec querySpec) {
		return null;
	}

	@Override
	protected BootGroupDtoList findWithRelationAdnSpec(RelationQuery rq, QuerySpec querySpec) {
		return null;
	}
}
