package com.jianglibo.wx.katharsis.repository;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.groups.Default;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.jianglibo.wx.config.userdetail.BootUserDetailManager;
import com.jianglibo.wx.domain.BootUser;
import com.jianglibo.wx.domain.FollowRelation;
import com.jianglibo.wx.facade.BootUserFacadeRepository;
import com.jianglibo.wx.facade.FollowRelationFacadeRepository;
import com.jianglibo.wx.katharsis.dto.UserDto;
import com.jianglibo.wx.katharsis.dto.UserDto.OnCreateGroup;
import com.jianglibo.wx.katharsis.dto.converter.UserDtoConverter;
import com.jianglibo.wx.katharsis.repository.UserDtoRepository.UserDtoList;
import com.jianglibo.wx.util.QuerySpecUtil;
import com.jianglibo.wx.util.UuidUtil;
import com.jianglibo.wx.util.QuerySpecUtil.RelationQuery;
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
			getRepository().updatePassword(dto.getId(), passwordEncoder.encode(dto.getPassword()));
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
	protected UserDtoList findWithRelationAdnSpec(RelationQuery rq, QuerySpec querySpec) {
		UserDto udt = new UserDto();
		udt.setId(rq.getRelationIds().get(0));
		if ("bootGroups".equals(rq.getRelationName())) {
			List<BootUser> posts = getRepository().findAllByGroup(rq.getRelationIds().get(0), querySpec.getOffset(), querySpec.getLimit(), QuerySpecUtil.getSortBrokers(querySpec));
			long count = getRepository().countByGroup(rq.getRelationIds().get(0), querySpec.getOffset(), querySpec.getLimit(), QuerySpecUtil.getSortBrokers(querySpec));
			return convertToResourceList(posts, count);
		} else if ("followersOp".equals(rq.getRelationName())) {
			List<FollowRelation> followers = followRelationDtoRepository.findByFollowed(rq.getRelationIds().get(0), querySpec.getOffset(), querySpec.getLimit(), QuerySpecUtil.getSortBrokers(querySpec));
			long count = followRelationDtoRepository.countByFollowed(rq.getRelationIds().get(0), querySpec.getOffset(), querySpec.getLimit(), QuerySpecUtil.getSortBrokers(querySpec));
			List<BootUser> users = followers.stream().map(fr -> fr.getFollowed()).collect(Collectors.toList());
			return convertToResourceList(users, count, udt);
		} else if ("followedsOp".equals(rq.getRelationName())) {
			List<FollowRelation> followers = followRelationDtoRepository.findByFollower(rq.getRelationIds().get(0), querySpec.getOffset(), querySpec.getLimit(), QuerySpecUtil.getSortBrokers(querySpec));
			long count = followRelationDtoRepository.countByFollower(rq.getRelationIds().get(0), querySpec.getOffset(), querySpec.getLimit(), QuerySpecUtil.getSortBrokers(querySpec));
			List<BootUser> users = followers.stream().map(fr -> fr.getFollowed()).collect(Collectors.toList());
			return convertToResourceList(users, count, udt);
		}
		return null;
	}
	
	/**
	 * treat katharsis framework.
	 * 
	 * @param entities
	 * @param count
	 * @param udt
	 * @return
	 */
	protected UserDtoList convertToResourceList(List<BootUser> entities, long count, UserDto udt) {
		List<UserDto> list = entities.stream().map(entity -> {
				UserDto ud = getConverter().entity2Dto(entity);
				ud.setFollowedsOp(udt);
				ud.setFollowersOp(udt);
				return ud;
			}).collect(Collectors.toList());		
		UserDtoList listOb = null;
		listOb = new UserDtoList();
		listOb.setMeta(new DtoListMeta(count));
		listOb.setLinks(new DtoListLinks());
		listOb.addAll(list);
		return listOb;
	}
}
