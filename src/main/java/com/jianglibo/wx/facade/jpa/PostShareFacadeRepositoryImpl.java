package com.jianglibo.wx.facade.jpa;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import com.jianglibo.wx.constant.PreAuthorizeExpression;
import com.jianglibo.wx.domain.BootUser;
import com.jianglibo.wx.domain.Post;
import com.jianglibo.wx.domain.PostShare;
import com.jianglibo.wx.facade.Page;
import com.jianglibo.wx.facade.PageFacade;
import com.jianglibo.wx.facade.PostShareFacadeRepository;
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
	public PostShare save(PostShare entity, PostShareDto dto) {
		return super.save(entity, dto);
	}

	@Override
	public PostShare newByDto(PostShareDto dto) {
		return null;
	}

	@Override
	public Page<PostShare> findByBootUser(BootUser user, PageFacade pf) {
		org.springframework.data.domain.Page<PostShare> opage = getRepository().findAllByBootUser(user, new SimplePageable(pf));
		return new Page<>(opage.getTotalElements(), opage.getContent());
	}

	@Override
	public Page<PostShare> findByPost(Post post, PageFacade pf) {
		org.springframework.data.domain.Page<PostShare> opage = getRepository().findAllByPost(post, new SimplePageable(pf));
		return new Page<>(opage.getTotalElements(), opage.getContent()); 
	}

	@Override
	public PostShare findByPostAndBootUser(Post post, BootUser user) {
		return getRepository().findByPostAndBootUser(post, user);
	}
}
