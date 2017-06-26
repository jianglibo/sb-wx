package com.jianglibo.wx.facade.jpa;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import com.jianglibo.wx.constant.PreAuthorizeExpression;
import com.jianglibo.wx.domain.BootUser;
import com.jianglibo.wx.domain.Post;
import com.jianglibo.wx.domain.PostShare;
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
	public List<PostShare> findByBootUser(BootUser user, long offset, long limit, SortBroker... sortBrokers) {
		return getRepository().findAllByBootUser(user, new SimplePageable(offset, limit, sortBrokers));
	}

	@Override
	public long countByBootUser(BootUser user) {
		return getRepository().countByBootUser(user);
	}

	@Override
	public List<PostShare> findByPost(Post post, long offset, long limit, SortBroker... sortBrokers) {
		return getRepository().findAllByPost(post, new SimplePageable(offset, limit, sortBrokers));
	}

	@Override
	public long countByPost(Post post) {
		return getRepository().countByPost(post);
	}

	@Override
	public PostShare findByPostAndBootUser(Post post, BootUser user) {
		return getRepository().findByPostAndBootUser(post, user);
	}
}
