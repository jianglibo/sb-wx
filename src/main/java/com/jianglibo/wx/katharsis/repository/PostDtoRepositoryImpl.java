package com.jianglibo.wx.katharsis.repository;


import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jianglibo.wx.domain.BootUser;
import com.jianglibo.wx.domain.Post;
import com.jianglibo.wx.domain.PostShare;
import com.jianglibo.wx.facade.BootUserFacadeRepository;
import com.jianglibo.wx.facade.PostFacadeRepository;
import com.jianglibo.wx.facade.PostShareFacadeRepository;
import com.jianglibo.wx.katharsis.dto.PostDto;
import com.jianglibo.wx.katharsis.dto.UserDto;
import com.jianglibo.wx.katharsis.dto.converter.DtoConverter.Scenario;
import com.jianglibo.wx.katharsis.dto.converter.PostDtoConverter;
import com.jianglibo.wx.katharsis.repository.PostDtoRepository.PostDtoList;
import com.jianglibo.wx.util.QuerySpecUtil;
import com.jianglibo.wx.util.QuerySpecUtil.RelationQuery;

import io.katharsis.queryspec.QuerySpec;

@Component
public class PostDtoRepositoryImpl  extends DtoRepositoryBase<PostDto, PostDtoList, Post, PostFacadeRepository> implements PostDtoRepository {
	
	@Autowired
	private PostShareFacadeRepository psRepo;
	
	@Autowired
	private BootUserFacadeRepository userRepo;
	
	@Autowired
	public PostDtoRepositoryImpl(PostFacadeRepository repository, PostDtoConverter converter) {
		super(PostDto.class, PostDtoList.class, Post.class, repository, converter);
	}

	@Override
	protected PostDtoList findAllWithQuerySpec(QuerySpec querySpec) {
		return null;
	}

	@Override
	protected List<String> checkAllSortableFieldAllowed(QuerySpec querySpec) {
		return null;
	}

	@Override
	protected PostDtoList findWithRelationAndSpec(RelationQuery rq, QuerySpec querySpec) {
		if ("creator".equals(rq.getRelationName())) {
			List<Post> posts = getRepository().findMine(userRepo.findOne(rq.getRelationIds().get(0)), querySpec.getOffset(), querySpec.getLimit(), QuerySpecUtil.getSortBrokers(querySpec));
			long count = getRepository().countMine(userRepo.findOne(rq.getRelationIds().get(0)));
			return convertToResourceList(posts, count, Scenario.RELATION_LIST);
		} else if ("sharedUsers".equals(rq.getRelationName())) {
			BootUser user = userRepo.findOne(rq.getRelationIds().get(0));
			List<PostShare> pss = psRepo.findByBootUser(user, querySpec.getOffset(), querySpec.getLimit(), QuerySpecUtil.getSortBrokers(querySpec));
			long count = psRepo.countByBootUser(user);
			PostDtoList pdl = convertToResourceList(pss.stream().map(ps -> ps.getPost()).collect(Collectors.toList()), count, Scenario.RELATION_LIST);
			List<UserDto> users = Arrays.asList(new UserDto(user.getId()));
			pdl.forEach(p -> p.setSharedUsers(users));
			return pdl;
		}
		return null;
	}
}
