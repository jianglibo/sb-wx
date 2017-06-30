package com.jianglibo.wx.katharsis.repository;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jianglibo.wx.domain.BootGroup;
import com.jianglibo.wx.domain.BootUser;
import com.jianglibo.wx.domain.GroupUserRelation;
import com.jianglibo.wx.facade.BootGroupFacadeRepository;
import com.jianglibo.wx.facade.BootUserFacadeRepository;
import com.jianglibo.wx.facade.GroupUserRelationFacadeRepository;
import com.jianglibo.wx.facade.Page;
import com.jianglibo.wx.katharsis.dto.GroupDto;
import com.jianglibo.wx.katharsis.dto.UserDto;
import com.jianglibo.wx.katharsis.dto.converter.DtoConverter.Scenario;
import com.jianglibo.wx.katharsis.dto.converter.GroupDtoConverter;
import com.jianglibo.wx.katharsis.repository.GroupDtoRepository.GroupDtoList;
import com.jianglibo.wx.util.QuerySpecUtil;
import com.jianglibo.wx.util.QuerySpecUtil.RelationQuery;

import io.katharsis.queryspec.QuerySpec;

@Component
public class GroupDtoRepositoryImpl  extends DtoRepositoryBase<GroupDto, GroupDtoList, BootGroup, BootGroupFacadeRepository> implements GroupDtoRepository {
	
	@Autowired
	private BootUserFacadeRepository userRepo;
	
	@Autowired
	private GroupUserRelationFacadeRepository guRepo;
	
	@Autowired
	public GroupDtoRepositoryImpl(BootGroupFacadeRepository repository, GroupDtoConverter converter) {
		super(GroupDto.class, GroupDtoList.class, BootGroup.class, repository, converter);
	}

	@Override
	protected GroupDtoList findAllWithQuerySpec(QuerySpec querySpec) {
		return null;
	}

	@Override
	protected List<String> checkAllSortableFieldAllowed(QuerySpec querySpec) {
		return null;
	}

	@Override
	protected GroupDtoList findWithRelationAndSpec(RelationQuery rq, QuerySpec querySpec) {
		if ("members".equals(rq.getRelationName())) {
			UserDto udo = new UserDto(rq.getRelationIds().get(0));
			BootUser bu = userRepo.findOne(udo.getId(), true);
			Page<GroupUserRelation> gurs = guRepo.findByBootUser(bu, QuerySpecUtil.getPageFacade(querySpec));
			List<BootGroup> gps = gurs.getContent().stream().map(gur -> gur.getBootGroup()).collect(Collectors.toList());
			GroupDtoList gl = convertToResourceList(gps, gurs.getTotalResourceCount(), Scenario.RELATION_LIST);
			gl.forEach(gdto -> gdto.getMembers().add(udo));
			return gl;
		}
		return null;
	}
}
