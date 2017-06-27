package com.jianglibo.wx.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.jianglibo.wx.domain.BootUser;
import com.jianglibo.wx.domain.Post;
import com.jianglibo.wx.domain.PostShare;

public interface PostShareRepository extends RepositoryBase<PostShare> {

	List<PostShare> findAllByBootUser(BootUser user, Pageable pageable);
	long countByBootUser(BootUser user);
	
	List<PostShare> findAllByPost(Post post, Pageable pageable);
	long countByPost(Post post);


	PostShare findByPostAndBootUser(Post post, BootUser user);
	List<PostShare> findAllByBootUser(BootUser user);
}
