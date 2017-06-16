package com.jianglibo.wx.repository;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;

import com.jianglibo.wx.domain.Post;

/**
 * To handle some conditions, not all conditions.
 * @author jianglibo@gmail.com
 *
 */
public class PostRepositoryImpl extends SimpleJpaRepositoryBase<Post> implements RepositoryBase<Post> {

	@Autowired
	public PostRepositoryImpl(EntityManager entityManager) {
		super(Post.class, entityManager);
	}

}
