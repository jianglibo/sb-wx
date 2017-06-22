package com.jianglibo.wx.facade.jpa;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import com.jianglibo.wx.constant.PreAuthorizeExpression;
import com.jianglibo.wx.domain.BootUser;
import com.jianglibo.wx.domain.Approve;
import com.jianglibo.wx.domain.Approve_;
import com.jianglibo.wx.facade.ApproveFacadeRepository;
import com.jianglibo.wx.facade.BootUserFacadeRepository;
import com.jianglibo.wx.facade.SimplePageable;
import com.jianglibo.wx.facade.SortBroker;
import com.jianglibo.wx.repository.ApproveRepository;
import com.jianglibo.wx.katharsis.dto.ApproveDto;

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
	@PreAuthorize(PreAuthorizeExpression.HAS_ADMINISTRATOR_ROLE)
	public Approve save(Approve entity) {
		return super.save(entity);
	}

	@Override
	public Approve patch(Approve entity, ApproveDto dto) {
//		entity.setName(dto.getName());
		return entity;
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
	public List<Approve> findSent(BootUser user, long offset, Long limit, SortBroker... sortBrokers) {
		return getRepository().findAllByRequester(user, new SimplePageable(offset, limit, sortBrokers));
	}

	@Override
	public long countSend(BootUser user) {
		return getRepository().countByRequester(user);
	}

	@Override
	public List<Approve> findReceived(BootUser user, long offset, Long limit, SortBroker... sortBrokers) {
		return getRepository().findAllByReceiver(user, new SimplePageable(offset, limit, sortBrokers));
	}

	@Override
	public long countReceived(BootUser user) {
		return getRepository().countByReceiver(user);
	}
}
