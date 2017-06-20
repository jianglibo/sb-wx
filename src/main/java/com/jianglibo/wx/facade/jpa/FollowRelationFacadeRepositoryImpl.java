package com.jianglibo.wx.facade.jpa;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import com.jianglibo.wx.constant.PreAuthorizeExpression;
import com.jianglibo.wx.domain.FollowRelation;
import com.jianglibo.wx.facade.BootUserFacadeRepository;
import com.jianglibo.wx.facade.FollowRelationFacadeRepository;
import com.jianglibo.wx.facade.SimplePageable;
import com.jianglibo.wx.facade.SortBroker;
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
	
	@Autowired
	private BootUserFacadeRepository userRepo;
	
	
	@Override
	@PreAuthorize(PreAuthorizeExpression.IS_FULLY_AUTHENTICATED)
	public FollowRelation save(FollowRelation entity) {
		return super.save(entity);
	}

	@Override
	public FollowRelation patch(FollowRelation entity, FollowRelationDto dto) {
		return null;
	}

	@Override
	public FollowRelation newByDto(FollowRelationDto dto) {
		return null;
	}

	@Override
	@PreAuthorize("hasRole('ADMINISTRATOR') or (#id == principal.id)")
	public List<FollowRelation> whosFollowedMe(@P("id") long userId, long offset, Long limit, SortBroker... sortBrokers) {
		return getRepository().findAllByBefollowed(userRepo.findOne(userId), new SimplePageable(offset, limit, sortBrokers));
	}

	@Override
	@PreAuthorize("hasRole('ADMINISTRATOR') or (#id == principal.id)")
	public long countWhosFollowedMe(@P("id")long userId, long offset, Long limit, SortBroker... sortBrokers) {		return getRepository().countByBefollowed(userRepo.findOne(userId));

	}

	@Override
	@PreAuthorize("hasRole('ADMINISTRATOR') or (#id == principal.id)")
	public List<FollowRelation> whosIFollowedTo(@P("id")long userId, long offset, Long limit, SortBroker... sortBrokers) {
		return getRepository().findAllByFollower(userRepo.findOne(userId), new SimplePageable(offset, limit, sortBrokers));
	}

	@Override
	@PreAuthorize("hasRole('ADMINISTRATOR') or (#id == principal.id)")
	public long countWhosIFollowedTo(@P("id")long userId, long offset, Long limit, SortBroker... sortBrokers) {
		return getRepository().countByFollower(userRepo.findOne(userId));
	}
}
