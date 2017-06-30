package com.jianglibo.wx.repository;

import org.springframework.data.domain.Pageable;

import com.jianglibo.wx.domain.Post;

public interface PostRepository extends RepositoryBase<Post> {

	org.springframework.data.domain.Page<Post> findAllByToAll(boolean toAll, Pageable pageable);

}
