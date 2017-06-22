package com.jianglibo.wx.katharsis.repository;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.groups.Default;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.jianglibo.wx.config.userdetail.BootUserDetailManager;
import com.jianglibo.wx.domain.BootUser;
import com.jianglibo.wx.domain.FollowRelation;
import com.jianglibo.wx.domain.PostShare;
import com.jianglibo.wx.facade.BootUserFacadeRepository;
import com.jianglibo.wx.facade.FollowRelationFacadeRepository;
import com.jianglibo.wx.facade.PostFacadeRepository;
import com.jianglibo.wx.facade.PostShareFacadeRepository;
import com.jianglibo.wx.katharsis.dto.GroupDto;
import com.jianglibo.wx.katharsis.dto.PostDto;
import com.jianglibo.wx.katharsis.dto.UserDto;
import com.jianglibo.wx.katharsis.dto.UserDto.OnCreateGroup;
import com.jianglibo.wx.katharsis.dto.converter.UserDtoConverter;
import com.jianglibo.wx.katharsis.repository.UserDtoRepository.UserDtoList;
import com.jianglibo.wx.util.QuerySpecUtil;
import com.jianglibo.wx.util.QuerySpecUtil.RelationQuery;
import com.jianglibo.wx.util.UuidUtil;
import com.jianglibo.wx.vo.BootUserPrincipal;

import io.katharsis.queryspec.QuerySpec;

@Component
public class UserDtoRepositoryImpl extends DtoRepositoryBase<UserDto, UserDtoList, BootUser, BootUserFacadeRepository> implements UserDtoRepository {
	
	private final BootUserDetailManager bootUserDetailManager;
	
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private FollowRelationFacadeRepository followRelationDtoRepository;
    
    @Autowired
    private PostFacadeRepository postRepo;
    
    @Autowired
    private PostShareFacadeRepository psRepo;
    
    @Autowired
    private BootUserFacadeRepository userRepo;
    
    @Autowired
	public UserDtoRepositoryImpl(BootUserFacadeRepository bootUserRepository, BootUserDetailManager bootUserDetailManager, UserDtoConverter converter) {
		super(UserDto.class, UserDtoList.class, BootUser.class, bootUserRepository, converter);
		this.bootUserDetailManager = bootUserDetailManager;
	}
    
	@Override
	public UserDto createNew(UserDto dto) {
		validate(dto, OnCreateGroup.class, Default.class);
		if (dto.getOpenId() == null || dto.getOpenId().isEmpty()) {
			dto.setOpenId(UuidUtil.uuidNoDash());
		}
		BootUserPrincipal bu = new BootUserPrincipal(dto);
		return getConverter().entity2Dto(bootUserDetailManager.createUserAndReturn(bu));
	}
	
	@Override
	public UserDto modify(UserDto dto) {
		if ("password".equals(dto.getDtoApplyTo())) {
			validate(dto, OnCreateGroup.class, Default.class);
			getRepository().updatePassword(userRepo.findOne(dto.getId()), passwordEncoder.encode(dto.getPassword()));
			dto.setPassword("");
			return dto;
		} else {
			validate(dto);
			BootUser entity = getRepository().findOne(dto.getId(), false);
			entity = getRepository().patch(entity, dto);
			return getConverter().entity2Dto(entity);
		}
	}

	@Override
	protected UserDtoList findAllWithQuerySpec(QuerySpec querySpec) {
		return null;
	}


	@Override
	protected List<String> checkAllSortableFieldAllowed(QuerySpec querySpec) {
		return null;
	}

	@Override
	protected UserDtoList findWithRelationAndSpec(RelationQuery rq, QuerySpec querySpec) {
		if ("bootGroups".equals(rq.getRelationName())) {
			GroupDto gdo = new GroupDto();
			gdo.setId(rq.getRelationIds().get(0));
			List<BootUser> users = getRepository().findAllByGroup(gdo.getId(), querySpec.getOffset(), querySpec.getLimit(), QuerySpecUtil.getSortBrokers(querySpec));
			long count = getRepository().countByGroup(rq.getRelationIds().get(0));
			UserDtoList udl = convertToResourceList(users, count);
			List<GroupDto> groups = Arrays.asList(gdo);
			udl.forEach(u -> u.setBootGroups(groups));
			return udl;
		} else if ("followersOp".equals(rq.getRelationName())) {
			UserDto udt = new UserDto();
			udt.setId(rq.getRelationIds().get(0));
			List<FollowRelation> followers = followRelationDtoRepository.findByFollowed(rq.getRelationIds().get(0), querySpec.getOffset(), querySpec.getLimit(), QuerySpecUtil.getSortBrokers(querySpec));
			long count = followRelationDtoRepository.countByFollowed(rq.getRelationIds().get(0));
			List<BootUser> users = followers.stream().map(fr -> fr.getFollowed()).collect(Collectors.toList());
			UserDtoList udl = convertToResourceList(users, count);
			udl.forEach(u -> {
				u.setFollowedsOp(udt);
				u.setFollowersOp(udt);
			});
			return udl;
		} else if ("followedsOp".equals(rq.getRelationName())) {
			UserDto udt = new UserDto();
			udt.setId(rq.getRelationIds().get(0));
			List<FollowRelation> followers = followRelationDtoRepository.findByFollower(rq.getRelationIds().get(0), querySpec.getOffset(), querySpec.getLimit(), QuerySpecUtil.getSortBrokers(querySpec));
			long count = followRelationDtoRepository.countByFollower(rq.getRelationIds().get(0));
			List<BootUser> users = followers.stream().map(fr -> fr.getFollowed()).collect(Collectors.toList());
			UserDtoList udl = convertToResourceList(users, count);
			udl.forEach(u -> {
				u.setFollowedsOp(udt);
				u.setFollowersOp(udt);
			});
			return udl;
		} else if ("receivedPosts".equals(rq.getRelationName())) {
			PostDto pd = new PostDto();
			pd.setId(rq.getRelationIds().get(0));
			List<PostShare> pss = psRepo.findByPost(postRepo.findOne(pd.getId()), querySpec.getOffset(), querySpec.getLimit(), QuerySpecUtil.getSortBrokers(querySpec));
			long count = psRepo.countByPost(postRepo.findOne(pd.getId()));
			UserDtoList udl = convertToResourceList(pss.stream().map(ps -> ps.getBootUser()).collect(Collectors.toList()), count);
			List<PostDto> posts = Arrays.asList(pd);
			udl.forEach(u -> u.setReceivedPosts(posts));
			return udl;
		}
		return null;
	}
}
