package com.jianglibo.wx.repository;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.jianglibo.wx.domain.Role;
import com.jianglibo.wx.domain.Role_;
import com.jianglibo.wx.facade.SimplePageable;
import com.jianglibo.wx.util.QuerySpecUtil;

import io.katharsis.queryspec.QuerySpec;

/**
 * To handle some conditions, not all conditions.
 * @author jianglibo@gmail.com
 *
 */
public class RoleRepositoryImpl extends SimpleJpaRepositoryBase<Role> implements RepositoryBase<Role> {

	@Autowired
	public RoleRepositoryImpl(EntityManager entityManager) {
		super(Role.class, entityManager);
	}

//	@Override
//	protected long countIfNotCountOne(QuerySpec querySpec) {
//		Specification<Role> spec = nameLike(QuerySpecUtil.getFilterStringValue(querySpec, "name"));
//		return countBySpecifiation(spec);
//	}
//
//	@Override
//	protected List<Role> findIfNotFindOne(QuerySpec querySpec) {
//		Pageable pageable = new SimplePageable(querySpec);
//		Specification<Role> spec = nameLike(QuerySpecUtil.getFilterStringValue(querySpec, "name"));
//		return findBySpecifiation(spec, pageable);
//	}
//
//	public Specification<Role> nameLike(Optional<String> name) {
//		return new Specification<Role>() {
//			public Predicate toPredicate(Root<Role> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
//				query.distinct(true);
//				if (name.isPresent() && name.get().trim().length() > 0) {
//					return builder.like(root.get(Role_.name), name.get());
//				} else {
//					return null;
//				}
//			}
//		};
//	}

}
