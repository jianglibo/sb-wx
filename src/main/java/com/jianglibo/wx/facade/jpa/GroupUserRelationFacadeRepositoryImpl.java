package com.jianglibo.wx.facade.jpa;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import com.jianglibo.wx.constant.PreAuthorizeExpression;
import com.jianglibo.wx.domain.BootGroup;
import com.jianglibo.wx.domain.BootUser;
import com.jianglibo.wx.domain.GroupUserRelation;
import com.jianglibo.wx.facade.BootGroupFacadeRepository;
import com.jianglibo.wx.facade.BootUserFacadeRepository;
import com.jianglibo.wx.facade.GroupUserRelationFacadeRepository;
import com.jianglibo.wx.facade.SimplePageable;
import com.jianglibo.wx.facade.SortBroker;
import com.jianglibo.wx.katharsis.dto.GroupUserRelationDto;
import com.jianglibo.wx.repository.GroupUserRelationRepository;

/**
 * @author jianglibo@gmail.com
 *
 */
@Component
public class GroupUserRelationFacadeRepositoryImpl extends FacadeRepositoryBaseImpl<GroupUserRelation,GroupUserRelationDto, GroupUserRelationRepository> implements GroupUserRelationFacadeRepository {
	
	public GroupUserRelationFacadeRepositoryImpl(GroupUserRelationRepository jpaRepo) {
		super(jpaRepo);
	}
	
	@Autowired
	private BootUserFacadeRepository userRepo;
	
	@Autowired
	private BootGroupFacadeRepository groupRepo;
	
	@Override
	@PreAuthorize(PreAuthorizeExpression.IS_FULLY_AUTHENTICATED)
	public GroupUserRelation save(GroupUserRelation entity) {
		return super.save(entity);
	}

	@Override
	public GroupUserRelation patch(GroupUserRelation entity, GroupUserRelationDto dto) {
		return null;
	}

	@Override
	public GroupUserRelation newByDto(GroupUserRelationDto dto) {
		return null;
	}


	@Override
	public List<GroupUserRelation> findByBootGroup(@P("id") long groupId, long offset, Long limit, SortBroker... sortBrokers) {
		return getRepository().findAllByBootGroup(groupRepo.findOne(groupId), new SimplePageable(offset, limit, sortBrokers));
	}

	@Override
	public long countByBootGroup(@P("id")long groupId) {
		return getRepository().countByBootGroup(groupRepo.findOne(groupId));
	}

	@Override
	@PreAuthorize("hasRole('ADMINISTRATOR') or (#id == principal.id)")
	public List<GroupUserRelation> findByBootUser(@P("id")long userId, long offset, Long limit, SortBroker... sortBrokers) {
		return getRepository().findAllByBootUser(userRepo.findOne(userId), new SimplePageable(offset, limit, sortBrokers));
	}


	@Override
	@PreAuthorize("hasRole('ADMINISTRATOR') or (#id == principal.id)")
	public long countByBootUser(@P("id")long userId) {
		return getRepository().countByBootUser(userRepo.findOne(userId));
	}

	@Override
	public GroupUserRelation findByBootGroupAndBootUser(BootGroup group, BootUser user) {
		return getRepository().findByBootGroupAndBootUser(group, user);
	}
}
