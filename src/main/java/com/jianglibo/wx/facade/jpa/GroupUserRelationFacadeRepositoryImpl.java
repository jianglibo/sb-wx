package com.jianglibo.wx.facade.jpa;

import java.util.List;

import org.springframework.security.access.method.P;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import com.jianglibo.wx.constant.PreAuthorizeExpression;
import com.jianglibo.wx.domain.BootGroup;
import com.jianglibo.wx.domain.BootUser;
import com.jianglibo.wx.domain.GroupUserRelation;
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
	public List<GroupUserRelation> findByBootGroup(@P("entity") BootGroup group, long offset, Long limit, SortBroker... sortBrokers) {
		return getRepository().findAllByBootGroup(group, new SimplePageable(offset, limit, sortBrokers));
	}

	@Override
	public long countByBootGroup(@P("entity")BootGroup group) {
		return getRepository().countByBootGroup(group);
	}

	@Override
	@PreAuthorize("hasRole('ADMINISTRATOR') or (#entity.id == principal.id)")
	public List<GroupUserRelation> findByBootUser(@P("entity")BootUser user, long offset, Long limit, SortBroker... sortBrokers) {
		return getRepository().findAllByBootUser(user, new SimplePageable(offset, limit, sortBrokers));
	}


	@Override
	public long countByBootUser(@P("entity")BootUser user) {
		return getRepository().countByBootUser(user);
	}

	@Override
	public GroupUserRelation findByBootGroupAndBootUser(BootGroup group, BootUser user) {
		return getRepository().findByBootGroupAndBootUser(group, user);
	}

	@Override
	public List<GroupUserRelation> findByBootGroup(BootGroup bg) {
		return getRepository().findAllByBootGroup(bg);
	}
}
