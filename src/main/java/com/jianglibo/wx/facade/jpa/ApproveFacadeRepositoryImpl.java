package com.jianglibo.wx.facade.jpa;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.method.P;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import com.jianglibo.wx.constant.PreAuthorizeExpression;
import com.jianglibo.wx.domain.Approve;
import com.jianglibo.wx.domain.Approve_;
import com.jianglibo.wx.domain.BootUser;
import com.jianglibo.wx.facade.ApproveFacadeRepository;
import com.jianglibo.wx.facade.BootUserFacadeRepository;
import com.jianglibo.wx.facade.Page;
import com.jianglibo.wx.facade.PageFacade;
import com.jianglibo.wx.katharsis.dto.ApproveDto;
import com.jianglibo.wx.repository.ApproveRepository;

/**
 * @author jianglibo@gmail.com
 *
 */
@Component
public class ApproveFacadeRepositoryImpl extends FacadeRepositoryBaseImpl<Approve,ApproveDto, ApproveRepository> implements ApproveFacadeRepository {
	
	@Autowired
	private BootUserFacadeRepository userRepo;
	
	public ApproveFacadeRepositoryImpl(ApproveRepository jpaRepo) {
		super(jpaRepo);
	}
	
	@Override
	@PreAuthorize("((#entity.receiver.id == principal.id) and ((#entity.state == T(com.jianglibo.wx.eu.ApproveState).REJECT) or (#entity.state == T(com.jianglibo.wx.eu.ApproveState).APPROVED))) or ((#entity.requester.id == principal.id) and ((#entity.state == T(com.jianglibo.wx.eu.ApproveState).REQUEST_PENDING) or (#entity.state == T(com.jianglibo.wx.eu.ApproveState).INVITE_PENDING)))")
	public Approve save(@P("entity") Approve entity, ApproveDto dto) {
		return super.save(entity, dto);
	}
	
	public Specification<Approve> requestEq(BootUser user) {
		return new Specification<Approve>() {
			public Predicate toPredicate(Root<Approve> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
				query.distinct(true);
				return builder.equal(root.get(Approve_.requester), user);
			}
		};
	}

	@Override
	@PreAuthorize(PreAuthorizeExpression.IS_FULLY_AUTHENTICATED)
	public Approve newByDto(ApproveDto dto) {
		Approve entity = new Approve();
		BeanUtils.copyProperties(dto, entity, "receiver", "requester", "state");
		entity.setReceiver(userRepo.findOne(dto.getReceiver().getId()));
		entity.setRequester(userRepo.findOne(dto.getRequester().getId()));
		return entity;
	}

	@Override
	@PreAuthorize("#entity.id == principal.id")
	public Page<Approve> findSent(@P("entity") BootUser user, PageFacade pf) {
		org.springframework.data.domain.Page<Approve> opage = getRepository().findAllByRequester(user, new SimplePageable(pf)); 
		return new Page<>(opage.getTotalElements(), opage.getContent());
	}

	@Override
	@PreAuthorize("#entity.id == principal.id")
	public Page<Approve> findReceived(@P("entity") BootUser user, PageFacade pf) {
		org.springframework.data.domain.Page<Approve> opage = getRepository().findAllByReceiver(user, new SimplePageable(pf)); 
		return new Page<>(opage.getTotalElements(), opage.getContent()); 
	}
}
