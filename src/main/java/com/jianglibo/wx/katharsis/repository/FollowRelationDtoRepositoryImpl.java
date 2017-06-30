package com.jianglibo.wx.katharsis.repository;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jianglibo.wx.domain.BootUser;
import com.jianglibo.wx.domain.FollowRelation;
import com.jianglibo.wx.facade.BootUserFacadeRepository;
import com.jianglibo.wx.facade.FollowRelationFacadeRepository;
import com.jianglibo.wx.facade.Page;
import com.jianglibo.wx.katharsis.dto.FollowRelationDto;
import com.jianglibo.wx.katharsis.dto.UserDto;
import com.jianglibo.wx.katharsis.dto.converter.DtoConverter.Scenario;
import com.jianglibo.wx.katharsis.dto.converter.FollowRelationDtoConverter;
import com.jianglibo.wx.katharsis.repository.FollowRelationDtoRepository.FollowRelationDtoList;
import com.jianglibo.wx.util.QuerySpecUtil;
import com.jianglibo.wx.util.QuerySpecUtil.RelationQuery;

import io.katharsis.queryspec.QuerySpec;

@Component
public class FollowRelationDtoRepositoryImpl  extends DtoRepositoryBase<FollowRelationDto, FollowRelationDtoList, FollowRelation, FollowRelationFacadeRepository> implements FollowRelationDtoRepository {
	
	@Autowired
	private BootUserFacadeRepository userRepo;
	
	@Autowired
	public FollowRelationDtoRepositoryImpl(FollowRelationFacadeRepository repository, FollowRelationDtoConverter converter) {
		super(FollowRelationDto.class, FollowRelationDtoList.class, FollowRelation.class, repository, converter);
	}

	@Override
	protected FollowRelationDtoList findAllWithQuerySpec(QuerySpec querySpec) {
		return null;
	}

	@Override
	protected List<String> checkAllSortableFieldAllowed(QuerySpec querySpec) {
		return null;
	}

	@Override
	protected FollowRelationDtoList findWithRelationAndSpec(RelationQuery rq, QuerySpec querySpec) {
		if ("befollowed".equals(rq.getRelationName())) {
			UserDto udto = new UserDto(rq.getRelationIds().get(0));
			BootUser user = userRepo.findOne(udto.getId(), true);
			Page<FollowRelation> follow2me = getRepository().findByFollowed(user, QuerySpecUtil.getPageFacade(querySpec));
			return convertToResourceList(follow2me.getContent(), follow2me.getTotalResourceCount(), Scenario.RELATION_LIST);
		} else if ("follower".equals(rq.getRelationName())) {
			UserDto udto = new UserDto(rq.getRelationIds().get(0));
			BootUser user = userRepo.findOne(udto.getId(), true);
			Page<FollowRelation> follow2me = getRepository().findByFollower(user, QuerySpecUtil.getPageFacade(querySpec));
			return convertToResourceList(follow2me.getContent(), follow2me.getTotalResourceCount(), Scenario.RELATION_LIST);
		}
		return null;
	}
}
