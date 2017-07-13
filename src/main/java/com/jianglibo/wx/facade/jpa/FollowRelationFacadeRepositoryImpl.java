package com.jianglibo.wx.facade.jpa;

import org.springframework.security.access.method.P;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

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
	
	/**
	 * Only follower can initiator following relation.
	 */
	@Override
	@PreAuthorize("#entity.follower.id == principal.id")
	public FollowRelation save(@P("entity") FollowRelation entity, FollowRelationDto dto) {
		return super.save(entity, dto);
	}
	
	@Override
	@PreAuthorize("#entity.follower.id == principal.id")
	public void delete(FollowRelation entity) {
		super.delete(entity);
	}

	@Override
	public FollowRelation newByDto(FollowRelationDto dto) {
		return null;
	}


	@Override
	@PreAuthorize("#entity.id == principal.id")
	public Page<FollowRelation> findByFollowed(@P("entity") BootUser user, PageFacade pf) {
		org.springframework.data.domain.Page<FollowRelation> opage = getRepository().findAllByFollowed(user, new SimplePageable(pf)); 
		return new Page<>(opage.getTotalElements(), opage.getContent());
	}

	@Override
	@PreAuthorize("#entity.id == principal.id")
	public Page<FollowRelation> findByFollower(@P("entity")BootUser user, PageFacade pf) {
		org.springframework.data.domain.Page<FollowRelation> opage =  getRepository().findAllByFollower(user, new SimplePageable(pf));
		return new Page<>(opage.getTotalElements(), opage.getContent()); 
	}

	@Override
	public FollowRelation findByFollowedAndFollower(BootUser befollowed, BootUser follower) {
		return getRepository().findByFollowedAndFollower(befollowed, follower);
	}
}
