package com.jianglibo.wx.facade.jpa;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.method.P;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import com.jianglibo.wx.constant.PreAuthorizeExpression;
import com.jianglibo.wx.domain.BootUser;
import com.jianglibo.wx.domain.Medium;
import com.jianglibo.wx.domain.Post;
import com.jianglibo.wx.domain.Post_;
import com.jianglibo.wx.facade.BootUserFacadeRepository;
import com.jianglibo.wx.facade.MediumFacadeRepository;
import com.jianglibo.wx.facade.PostFacadeRepository;
import com.jianglibo.wx.facade.SimplePageable;
import com.jianglibo.wx.facade.SortBroker;
import com.jianglibo.wx.katharsis.dto.MediumDto;
import com.jianglibo.wx.katharsis.dto.PostDto;
import com.jianglibo.wx.repository.PostRepository;
import com.jianglibo.wx.util.SecurityUtil;

/**
 * @author jianglibo@gmail.com
 *
 */
@Component
public class PostFacadeRepositoryImpl extends FacadeRepositoryBaseImpl<Post,PostDto, PostRepository> implements PostFacadeRepository {
	
	@Autowired
	private BootUserFacadeRepository userRepo;
	
	@Autowired
	private MediumFacadeRepository mediumRepo;
	
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
	@PreAuthorize(PreAuthorizeExpression.ENTITY_ID_EQUAL_OR_HAS_ADMINISTRATOR_ROLE)
	public List<Post> findMine(@P("entity") BootUser user, long offset, Long limit, SortBroker... sortBrokers) {
		return getRepository().findAll(creatorEq(user), new SimplePageable(offset, limit, sortBrokers)).getContent();
	}

	@Override
	public long countMine(@P("entity") BootUser user) {
		return getRepository().count(creatorEq(user));
	}
	
	public Specification<Post> creatorEq(BootUser user) {
		return new Specification<Post>() {
			public Predicate toPredicate(Root<Post> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
				query.distinct(true);
				return builder.equal(root.get(Post_.creator), user);
			}
		};
	}


	@Override
	@PreAuthorize(PreAuthorizeExpression.IS_FULLY_AUTHENTICATED)
	public Post newByDto(PostDto dto) {
		Post entity = new Post();
		entity.setTitle(dto.getTitle());
		entity.setContent(dto.getContent());
		entity.setCreator(userRepo.findOne(SecurityUtil.getLoginUserId()));
		entity = getRepository().save(entity);
		
		List<Medium> media = new ArrayList<>();
		if (dto.getMedia() != null) {
			for(MediumDto mdto : dto.getMedia()) {
				Medium m = mediumRepo.findOne(mdto.getId());
				m.setPost(entity);
				media.add(mediumRepo.save(m));
			}
		}
		entity.setMedia(media);
		return entity;
	}
}
