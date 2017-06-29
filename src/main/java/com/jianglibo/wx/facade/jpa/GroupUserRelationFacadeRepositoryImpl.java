package com.jianglibo.wx.facade.jpa;

import org.springframework.security.access.method.P;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import com.jianglibo.wx.constant.PreAuthorizeExpression;
import com.jianglibo.wx.domain.BootGroup;
import com.jianglibo.wx.domain.BootUser;
import com.jianglibo.wx.domain.GroupUserRelation;
import com.jianglibo.wx.facade.GroupUserRelationFacadeRepository;
import com.jianglibo.wx.facade.Page;
import com.jianglibo.wx.facade.PageFacade;
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
	public GroupUserRelation save(GroupUserRelation entity, GroupUserRelationDto dto) {
		return super.save(entity, dto);
	}

	@Override
	public GroupUserRelation newByDto(GroupUserRelationDto dto) {
		return null;
	}


	@Override
	public Page<GroupUserRelation> findByBootGroup(@P("entity") BootGroup group, PageFacade pf) {
		org.springframework.data.domain.Page<GroupUserRelation> opage = getRepository().findAllByBootGroup(group, new SimplePageable(pf)); 
		return new Page<>(opage.getTotalElements(), opage.getContent());
	}

	@Override
	@PreAuthorize("hasRole('ADMINISTRATOR') or (#entity.id == principal.id)")
	public Page<GroupUserRelation> findByBootUser(@P("entity")BootUser user, PageFacade pf) {
		org.springframework.data.domain.Page<GroupUserRelation> opage = getRepository().findAllByBootUser(user, new SimplePageable(pf)); 
		return new Page<>(opage.getTotalElements(), opage.getContent());
	}

	@Override
	public GroupUserRelation findByBootGroupAndBootUser(BootGroup group, BootUser user) {
		return getRepository().findByBootGroupAndBootUser(group, user);
	}
}
