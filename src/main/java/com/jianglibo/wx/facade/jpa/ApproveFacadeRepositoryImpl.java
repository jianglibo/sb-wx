package com.jianglibo.wx.facade.jpa;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import com.jianglibo.wx.constant.PreAuthorizeExpression;
import com.jianglibo.wx.domain.BootUser;
import com.jianglibo.wx.domain.Approve;
import com.jianglibo.wx.domain.Approve_;
import com.jianglibo.wx.facade.ApproveFacadeRepository;
import com.jianglibo.wx.facade.SortBroker;
import com.jianglibo.wx.repository.ApproveRepository;
import com.jianglibo.wx.katharsis.dto.ApproveDto;

/**
 * @author jianglibo@gmail.com
 *
 */
@Component
public class ApproveFacadeRepositoryImpl extends FacadeRepositoryBaseImpl<Approve,ApproveDto, ApproveRepository> implements ApproveFacadeRepository {
	
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
		return entity;
	}

	@Override
	public List<Approve> findSent(BootUser user, long offset, Long limit, SortBroker... sortBrokers) {
		return null;
	}

	@Override
	public long countSend(BootUser user) {
		return 0;
	}

	@Override
	public List<Approve> findReceived(BootUser user, long offset, Long limit, SortBroker... sortBrokers) {
		return null;
	}

	@Override
	public long countReceived(BootUser user) {
		return 0;
	}
}
