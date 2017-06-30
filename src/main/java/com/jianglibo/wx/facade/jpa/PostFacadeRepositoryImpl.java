package com.jianglibo.wx.facade.jpa;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.method.P;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import com.jianglibo.wx.constant.PreAuthorizeExpression;
import com.jianglibo.wx.domain.BootUser;
import com.jianglibo.wx.domain.Medium;
import com.jianglibo.wx.domain.Post;
import com.jianglibo.wx.domain.PostShare;
import com.jianglibo.wx.domain.Post_;
import com.jianglibo.wx.domain.Unread;
import com.jianglibo.wx.facade.BootUserFacadeRepository;
import com.jianglibo.wx.facade.MediumFacadeRepository;
import com.jianglibo.wx.facade.Page;
import com.jianglibo.wx.facade.PageFacade;
import com.jianglibo.wx.facade.PostFacadeRepository;
import com.jianglibo.wx.facade.PostShareFacadeRepository;
import com.jianglibo.wx.katharsis.dto.MediumDto;
import com.jianglibo.wx.katharsis.dto.PostDto;
import com.jianglibo.wx.repository.PostRepository;
import com.jianglibo.wx.util.PropertyCopyUtil;
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
	
	@Autowired
	private PostShareFacadeRepository psRepo;
	
	@Autowired
	private BootGroupFacadeRepositoryImpl groupRepo;
	
	@Autowired
	private GroupUserRelationFacadeRepositoryImpl guRepo;
	
	@Autowired
	private UnreadFacadeRepositoryImpl unreadRepo;
	
	public PostFacadeRepositoryImpl(PostRepository jpaRepo) {
		super(jpaRepo);
	}
	
	@Override
	@PreAuthorize(PreAuthorizeExpression.HAS_ADMINISTRATOR_ROLE)
	public Post save(Post entity, PostDto dto) {
		return super.save(entity, dto);
	}

	@Override
	@PreAuthorize(PreAuthorizeExpression.ENTITY_ID_EQUAL_OR_HAS_ADMINISTRATOR_ROLE)
	public Page<Post> findMine(@P("entity") BootUser user, PageFacade pf) {
		org.springframework.data.domain.Page<Post> opage = getRepository().findAll(creatorEq(user), new SimplePageable(pf)); 
		return new Page<>(opage.getTotalElements(), opage.getContent());
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
	public Post findOne(Long id, boolean internalCall) {
		Post post =  super.findOne(id, true);
		if (post.isToAll() || post.getCreator().getId().equals(SecurityUtil.getLoginUserId())) {
			return post;
		}
		
		BootUser currentUser = userRepo.findOne(SecurityUtil.getLoginUserId(), true);
		PostShare ps = psRepo.findByPostAndBootUser(post, currentUser);
		if (ps == null) {
			throw new AccessDeniedException("you cannot access a post people without share to you.");
		}
		return post;
	}


	@Override
	@PreAuthorize(PreAuthorizeExpression.IS_FULLY_AUTHENTICATED)
	public Post newByDto(PostDto dto) {
		Post entity = new Post();
		PropertyCopyUtil.copyPropertyWhenCreate(entity, dto);
		entity.setCreator(userRepo.findOne(SecurityUtil.getLoginUserId(), true));
		entity = getRepository().save(entity);
		List<Medium> media = new ArrayList<>();
		if (dto.getMedia() != null) {
			for(MediumDto mdto : dto.getMedia()) {
				Medium m = mediumRepo.findOne(mdto.getId(), true);
				m.setPost(entity);
				media.add(mediumRepo.save(m, null));
			}
		}
		entity.setMedia(media);
		
		final Post fe = entity;

		if (dto.getSharedUsers() != null) {
			dto.getSharedUsers().stream().map(udto -> userRepo.findOne(udto.getId(), true)).forEach(u -> {
				saveSharePost(fe, u);
			});
		}
		
		if (dto.getSharedGroups() != null) {
			dto.getSharedGroups().stream()
				.map(gdto -> groupRepo.findOne(gdto.getId(), true))
				.flatMap(g -> guRepo.findByBootGroup(g, new PageFacade(10000L)).getContent().stream())
				.map(gu -> gu.getBootUser()).forEach(bu -> {
					saveSharePost(fe, bu);
				});
		}
		
		return entity;
	}
	
	public void saveSharePost(final Post fe, BootUser user) {
		try {
			PostShare ps = new PostShare(fe, user);
			psRepo.save(ps, null);
			Unread ur = new Unread();
			ur.setBootUser(user);
			ur.setObid(fe.getId());
			ur.setType(Post.class.getName());
			unreadRepo.save(ur, null);
		} catch (Exception e) {
			
		}
	}

	@PreAuthorize(PreAuthorizeExpression.IS_FULLY_AUTHENTICATED + " and #toAll")
	@Override
	public Page<Post> findAllByToAll(@P("toAll")boolean toAll, PageFacade pageFacade) {
		org.springframework.data.domain.Page<Post> opage = getRepository().findAllByToAll(toAll, new SimplePageable(pageFacade));
		return new Page<>(opage.getTotalElements(), opage.getContent());
	}
}
