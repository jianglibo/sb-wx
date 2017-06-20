package com.jianglibo.wx.katharsis.repository;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jianglibo.wx.domain.FollowRelation;
import com.jianglibo.wx.facade.FollowRelationFacadeRepository;
import com.jianglibo.wx.katharsis.dto.FollowRelationDto;
import com.jianglibo.wx.katharsis.dto.converter.FollowRelationDtoConverter;
import com.jianglibo.wx.katharsis.repository.FollowRelationDtoRepository.FollowRelationDtoList;
import com.jianglibo.wx.util.QuerySpecUtil;
import com.jianglibo.wx.util.QuerySpecUtil.RelationQuery;

import io.katharsis.queryspec.QuerySpec;

@Component
public class FollowRelationDtoRepositoryImpl  extends DtoRepositoryBase<FollowRelationDto, FollowRelationDtoList, FollowRelation, FollowRelationFacadeRepository> implements FollowRelationDtoRepository {
	
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
	protected FollowRelationDtoList findWithRelationAdnSpec(RelationQuery rq, QuerySpec querySpec) {
		if ("befollowed".equals(rq.getRelationName())) {
			List<FollowRelation> follow2me = getRepository().findByFollowed(rq.getRelationIds().get(0), querySpec.getOffset(), querySpec.getLimit(), QuerySpecUtil.getSortBrokers(querySpec));
			long count = getRepository().countByFollowed(rq.getRelationIds().get(0), querySpec.getOffset(), querySpec.getLimit(), QuerySpecUtil.getSortBrokers(querySpec));
			return convertToResourceList(follow2me, count);
		} else if ("follower".equals(rq.getRelationName())) {
			List<FollowRelation> follow2me = getRepository().findByFollower(rq.getRelationIds().get(0), querySpec.getOffset(), querySpec.getLimit(), QuerySpecUtil.getSortBrokers(querySpec));
			long count = getRepository().countByFollower(rq.getRelationIds().get(0), querySpec.getOffset(), querySpec.getLimit(), QuerySpecUtil.getSortBrokers(querySpec));
			return convertToResourceList(follow2me, count);
		}
		return null;
	}
}
