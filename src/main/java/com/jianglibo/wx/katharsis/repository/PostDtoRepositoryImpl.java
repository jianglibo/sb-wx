package com.jianglibo.wx.katharsis.repository;


import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jianglibo.wx.domain.BootUser;
import com.jianglibo.wx.domain.Post;
import com.jianglibo.wx.domain.PostShare;
import com.jianglibo.wx.domain.Unread;
import com.jianglibo.wx.facade.BootUserFacadeRepository;
import com.jianglibo.wx.facade.Page;
import com.jianglibo.wx.facade.PostFacadeRepository;
import com.jianglibo.wx.facade.PostShareFacadeRepository;
import com.jianglibo.wx.facade.UnreadFacadeRepository;
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
	private UnreadFacadeRepository unreadRepo;
	
	@Autowired
	public PostDtoRepositoryImpl(PostFacadeRepository repository, PostDtoConverter converter) {
		super(PostDto.class, PostDtoList.class, Post.class, repository, converter);
	}

	@Override
	protected PostDtoList findAllWithQuerySpec(QuerySpec querySpec) {
		boolean toAll = (boolean) QuerySpecUtil.getFilterSingleValue(querySpec, "toAll").orElse(true);
		Page<Post> posts = getRepository().findAllByToAll(toAll, QuerySpecUtil.getPageFacade(querySpec));
		return convertToResourceList(posts, Scenario.FIND_LIST);
	}

	@Override
	protected List<String> checkAllSortableFieldAllowed(QuerySpec querySpec) {
		return null;
	}

	@Override
	protected PostDtoList findWithRelationAndSpec(RelationQuery rq, QuerySpec querySpec) {
		if ("creator".equals(rq.getRelationName())) {
			BootUser user = userRepo.findOne(rq.getRelationIds().get(0), true);
			Page<Post> posts = getRepository().findMine(user, QuerySpecUtil.getPageFacade(querySpec));
			return convertToResourceList(posts, Scenario.RELATION_LIST);
		} else if ("sharedUsers".equals(rq.getRelationName())) {
			BootUser user = userRepo.findOne(rq.getRelationIds().get(0), true);
			Page<PostShare> pss = psRepo.findByBootUser(user, QuerySpecUtil.getPageFacade(querySpec));
			List<Post> postlist = pss.getContent().stream().map(ps -> ps.getPost()).collect(Collectors.toList());
			Page<Post> posts = new Page<>(pss.getTotalResourceCount(), postlist);
			
			PostDtoList pdl = convertToResourceList(posts, Scenario.RELATION_LIST);
			List<UserDto> users = Arrays.asList(new UserDto(user.getId()));
			pdl.forEach(p -> {
				p.setSharedUsers(users);
				boolean hasRead = unreadRepo.userHasReadThisPost(user, p.getId());
				p.setRead(hasRead);
			});
			return pdl;
		}
		return null;
	}
}
