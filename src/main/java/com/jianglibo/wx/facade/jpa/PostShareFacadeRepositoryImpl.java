package com.jianglibo.wx.facade.jpa;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import com.jianglibo.wx.constant.PreAuthorizeExpression;
import com.jianglibo.wx.domain.BootGroup;
import com.jianglibo.wx.domain.BootUser;
import com.jianglibo.wx.domain.PostShare;
import com.jianglibo.wx.facade.BootGroupFacadeRepository;
import com.jianglibo.wx.facade.BootUserFacadeRepository;
import com.jianglibo.wx.facade.PostShareFacadeRepository;
import com.jianglibo.wx.facade.SimplePageable;
import com.jianglibo.wx.facade.SortBroker;
import com.jianglibo.wx.katharsis.dto.PostShareDto;
import com.jianglibo.wx.repository.PostShareRepository;

/**
 * @author jianglibo@gmail.com
 *
 */
@Component
public class PostShareFacadeRepositoryImpl extends FacadeRepositoryBaseImpl<PostShare,PostShareDto, PostShareRepository> implements PostShareFacadeRepository {
	
	public PostShareFacadeRepositoryImpl(PostShareRepository jpaRepo) {
		super(jpaRepo);
	}
	
	@Autowired
	private BootUserFacadeRepository userRepo;
	
	@Autowired
	private BootGroupFacadeRepository groupRepo;
	
	@Override
	@PreAuthorize(PreAuthorizeExpression.IS_FULLY_AUTHENTICATED)
	public PostShare save(PostShare entity) {
		return super.save(entity);
	}

	@Override
	public PostShare patch(PostShare entity, PostShareDto dto) {
		return null;
	}

	@Override
	public PostShare newByDto(PostShareDto dto) {
		return null;
	}


	@Override
	public List<PostShare> findByBootGroup(@P("id") long groupId, long offset, Long limit, SortBroker... sortBrokers) {
		return getRepository().findAllByBootGroup(groupRepo.findOne(groupId), new SimplePageable(offset, limit, sortBrokers));
	}

	@Override
	public long countByBootGroup(@P("id")long groupId) {
		return getRepository().countByBootGroup(groupRepo.findOne(groupId));
	}

	@Override
	@PreAuthorize("hasRole('ADMINISTRATOR') or (#id == principal.id)")
	public List<PostShare> findByBootUser(@P("id")long userId, long offset, Long limit, SortBroker... sortBrokers) {
		return getRepository().findAllByBootUser(userRepo.findOne(userId), new SimplePageable(offset, limit, sortBrokers));
	}


	@Override
	@PreAuthorize("hasRole('ADMINISTRATOR') or (#id == principal.id)")
	public long countByBootUser(@P("id")long userId) {
		return getRepository().countByBootUser(userRepo.findOne(userId));
	}

	@Override
	public PostShare findByBootGroupAndBootUser(BootGroup group, BootUser user) {
		return getRepository().findByBootGroupAndBootUser(group, user);
	}
}
