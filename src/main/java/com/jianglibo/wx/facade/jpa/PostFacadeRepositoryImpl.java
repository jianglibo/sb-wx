package com.jianglibo.wx.facade.jpa;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.method.P;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import com.jianglibo.wx.constant.PreAuthorizeExpression;
import com.jianglibo.wx.domain.BootUser_;
import com.jianglibo.wx.domain.Post;
import com.jianglibo.wx.domain.Post_;
import com.jianglibo.wx.facade.PostFacadeRepository;
import com.jianglibo.wx.facade.SimplePageable;
import com.jianglibo.wx.facade.SortBroker;
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
	@PreAuthorize(PreAuthorizeExpression.ID_EQUAL_OR_HAS_ADMINISTRATOR_ROLE)
	public List<Post> findMine(@P("id") long userId, long offset, Long limit, SortBroker... sortBrokers) {
		return getRepository().findAll(creatorEq(userId), new SimplePageable(offset, limit, sortBrokers)).getContent();
	}

	@Override
	public long countMine(@P("id") long userId, long offset, Long limit, SortBroker... sortBrokers) {
		return getRepository().count(creatorEq(userId));
	}
	
	public Specification<Post> creatorEq(long userId) {
		return new Specification<Post>() {
			public Predicate toPredicate(Root<Post> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
				query.distinct(true);
				return builder.equal(root.get(Post_.creator).get(BootUser_.id), userId);
			}
		};
	}


	@Override
	public Post newByDto(PostDto dto) {
		Post entity = new Post();
		return entity;
	}
}
