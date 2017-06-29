package com.jianglibo.wx.facade.jpa;

import org.springframework.security.access.method.P;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import com.jianglibo.wx.constant.PreAuthorizeExpression;
import com.jianglibo.wx.domain.BootUser;
import com.jianglibo.wx.domain.FollowRelation;
import com.jianglibo.wx.facade.FollowRelationFacadeRepository;
import com.jianglibo.wx.facade.Page;
import com.jianglibo.wx.facade.PageFacade;
import com.jianglibo.wx.katharsis.dto.FollowRelationDto;
import com.jianglibo.wx.repository.FollowRelationRepository;

/**
 * @author jianglibo@gmail.com
 *
 */
@Component
public class FollowRelationFacadeRepositoryImpl extends FacadeRepositoryBaseImpl<FollowRelation,FollowRelationDto, FollowRelationRepository> implements FollowRelationFacadeRepository {
	
	public FollowRelationFacadeRepositoryImpl(FollowRelationRepository jpaRepo) {
		super(jpaRepo);
	}
	
	@Override
	@PreAuthorize(PreAuthorizeExpression.IS_FULLY_AUTHENTICATED)
	public FollowRelation save(FollowRelation entity, FollowRelationDto dto) {
		return super.save(entity, dto);
	}

	@Override
	public FollowRelation newByDto(FollowRelationDto dto) {
		return null;
	}


	@Override
	@PreAuthorize("hasRole('ADMINISTRATOR') or (#entity.id == principal.id)")
	public Page<FollowRelation> findByFollowed(@P("entity") BootUser user, PageFacade pf) {
		org.springframework.data.domain.Page<FollowRelation> opage = getRepository().findAllByFollowed(user, new SimplePageable(pf)); 
		return new Page<>(opage.getTotalElements(), opage.getContent());
	}

//	@Override
//	@PreAuthorize("hasRole('ADMINISTRATOR') or (#entity.id == principal.id)")
//	public long countByFollower(@P("entity")BootUser user) {
//		return getRepository().countByFollowed(user);
//	}

	@Override
	@PreAuthorize("hasRole('ADMINISTRATOR') or (#entity.id == principal.id)")
	public Page<FollowRelation> findByFollower(@P("entity")BootUser user, PageFacade pf) {
		org.springframework.data.domain.Page<FollowRelation> opage =  getRepository().findAllByFollower(user, new SimplePageable(pf));
		return new Page<>(opage.getTotalElements(), opage.getContent()); 
	}


//	@Override
//	@PreAuthorize("hasRole('ADMINISTRATOR') or (#entity.id == principal.id)")
//	public long countByFollowed(@P("entity")BootUser user) {
//		return getRepository().countByFollower(user);
//	}

	@Override
	public FollowRelation findByFollowedAndFollower(BootUser befollowed, BootUser follower) {
		return getRepository().findByFollowedAndFollower(befollowed, follower);
	}
}
