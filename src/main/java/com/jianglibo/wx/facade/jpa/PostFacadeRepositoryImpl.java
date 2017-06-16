package com.jianglibo.wx.facade.jpa;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import com.jianglibo.wx.constant.PreAuthorizeExpression;
import com.jianglibo.wx.domain.Post;
import com.jianglibo.wx.facade.PostFacadeRepository;
import com.jianglibo.wx.katharsis.dto.PostDto;
import com.jianglibo.wx.repository.PostRepository;

/**
 * @author jianglibo@gmail.com
 *
 */
@Component
public class PostFacadeRepositoryImpl extends FacadeRepositoryBaseImpl<Post,PostDto, PostRepository> implements PostFacadeRepository {
	
	public PostFacadeRepositoryImpl(PostRepository jpaRepo) {
		super(jpaRepo);
	}
	
	@Override
	@PreAuthorize(PreAuthorizeExpression.HAS_ADMINISTRATOR_ROLE)
	public Post save(Post entity) {
		return super.save(entity);
	}

	@Override
	public Post patch(Post entity, PostDto dto) {
//		entity.setName(dto.getName());
		return entity;
	}

	@Override
	public Post newByDto(PostDto dto) {
		Post entity = new Post();
		return entity;
	}
}
