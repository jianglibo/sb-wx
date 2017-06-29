package com.jianglibo.wx.katharsis.repository;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.groups.Default;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.jianglibo.wx.config.userdetail.BootUserDetailManager;
import com.jianglibo.wx.domain.Approve;
import com.jianglibo.wx.domain.BootGroup;
import com.jianglibo.wx.domain.BootUser;
import com.jianglibo.wx.domain.FollowRelation;
import com.jianglibo.wx.domain.PostShare;
import com.jianglibo.wx.facade.ApproveFacadeRepository;
import com.jianglibo.wx.facade.BootGroupFacadeRepository;
import com.jianglibo.wx.facade.BootUserFacadeRepository;
import com.jianglibo.wx.facade.FollowRelationFacadeRepository;
import com.jianglibo.wx.facade.Page;
import com.jianglibo.wx.facade.PostFacadeRepository;
import com.jianglibo.wx.facade.PostShareFacadeRepository;
import com.jianglibo.wx.katharsis.dto.GroupDto;
import com.jianglibo.wx.katharsis.dto.PostDto;
import com.jianglibo.wx.katharsis.dto.UserDto;
import com.jianglibo.wx.katharsis.dto.UserDto.OnCreateGroup;
import com.jianglibo.wx.katharsis.dto.converter.DtoConverter.Scenario;
import com.jianglibo.wx.katharsis.dto.converter.UserDtoConverter;
import com.jianglibo.wx.katharsis.repository.UserDtoRepository.UserDtoList;
import com.jianglibo.wx.util.PatchUtil;
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
    private BootGroupFacadeRepository groupRepo;
    
    @Autowired
    private ApproveFacadeRepository approveReop;
    
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
		BootUserPrincipal bup = new BootUserPrincipal(dto);
		BootUser bu = bootUserDetailManager.createUserAndReturn(bup);
		return getConverter().entity2Dto(bu, Scenario.NEW);
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
			PatchUtil.applyPatch(entity, dto);
			return getConverter().entity2Dto(entity, Scenario.MODIFY);
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
		if ("joinedGroups".equals(rq.getRelationName())) {
			GroupDto gdo = new GroupDto(rq.getRelationIds().get(0));
			BootGroup bg = groupRepo.findOne(gdo.getId());
			Page<BootUser> users = getRepository().findAllByGroup(bg, QuerySpecUtil.getPageFacade(querySpec));
			UserDtoList udl = convertToResourceList(users, Scenario.RELATION_LIST);
			List<GroupDto> groups = Arrays.asList(gdo);
			udl.forEach(u -> u.setJoinedGroups(groups));
			return udl;
		} else if ("followersOp".equals(rq.getRelationName())) {
			UserDto udt = new UserDto(rq.getRelationIds().get(0));
			BootUser user = userRepo.findOne(udt.getId());
			Page<FollowRelation> followerPage = followRelationDtoRepository.findByFollowed(user, QuerySpecUtil.getPageFacade(querySpec));
			List<BootUser> users = followerPage.getContent().stream().map(fr -> fr.getFollowed()).collect(Collectors.toList());
			UserDtoList udl = convertToResourceList(users, followerPage.getTotalResourceCount(), Scenario.RELATION_LIST);
			udl.forEach(u -> {
				u.setFollowedsOp(udt);
				u.setFollowersOp(udt);
			});
			return udl;
		} else if ("followedsOp".equals(rq.getRelationName())) {
			UserDto udt = new UserDto(rq.getRelationIds().get(0));
			BootUser user = userRepo.findOne(udt.getId());
			Page<FollowRelation> followerPage = followRelationDtoRepository.findByFollower(user, QuerySpecUtil.getPageFacade(querySpec));
			List<BootUser> users = followerPage.getContent().stream().map(fr -> fr.getFollowed()).collect(Collectors.toList());
			UserDtoList udl = convertToResourceList(users, followerPage.getTotalResourceCount(), Scenario.RELATION_LIST);
			udl.forEach(u -> {
				u.setFollowedsOp(udt);
				u.setFollowersOp(udt);
			});
			return udl;
		} else if ("receivedPosts".equals(rq.getRelationName())) {
			PostDto pd = new PostDto(rq.getRelationIds().get(0));
			Page<PostShare> pss = psRepo.findByPost(postRepo.findOne(pd.getId()), QuerySpecUtil.getPageFacade(querySpec));
			UserDtoList udl = convertToResourceList(pss.getContent().stream().map(ps -> ps.getBootUser()).collect(Collectors.toList()), pss.getTotalResourceCount(), Scenario.RELATION_LIST);
			List<PostDto> posts = Arrays.asList(pd);
			udl.forEach(u -> u.setReceivedPosts(posts));
			return udl;
		} else if ("sentApproves".equals(rq.getRelationName())) {
			Approve ap = approveReop.findOne(rq.getRelationIds().get(0));
			BootUser bu = ap.getRequester();
			UserDtoList udl = convertToResourceList(Arrays.asList(bu), 1, Scenario.RELATION_LIST);
			return udl;

		}
		return null;
	}
}
