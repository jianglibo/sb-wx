package com.jianglibo.wx.katharsis.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jianglibo.wx.domain.BootUser;
import com.jianglibo.wx.domain.Post;
import com.jianglibo.wx.domain.PostShare;
import com.jianglibo.wx.facade.BootUserFacadeRepository;
import com.jianglibo.wx.facade.PostFacadeRepository;
import com.jianglibo.wx.facade.PostShareFacadeRepository;
import com.jianglibo.wx.katharsis.dto.PostDto;
import com.jianglibo.wx.katharsis.dto.UserDto;

@Component
public class PostToUserRepositoryImpl extends RelationshipRepositoryBaseMine<PostDto, UserDto> {
	
	@Autowired
	private PostFacadeRepository postRepo;
	
	@Autowired
	private BootUserFacadeRepository userRepo;
	
	@Autowired
	private PostShareFacadeRepository psRepo;

	protected PostToUserRepositoryImpl() {
		super(PostDto.class, UserDto.class);
	}
	
	@Override
	public void addRelations(PostDto source, Iterable<Long> targetIds, String fieldName) {
		Post p = postRepo.findOne(source.getId());
		if ("sharedUsers".equals(fieldName)) {
			for(Long id : targetIds) {
				PostShare ps = new PostShare(p, userRepo.findOne(id));
				psRepo.save(ps);
			}
		}
	}
	
	@Override
	public void removeRelations(PostDto source, Iterable<Long> targetIds, String fieldName) {
		Post p = postRepo.findOne(source.getId());
		if ("sharedUsers".equals(fieldName)) {
			for(Long id : targetIds) {
				PostShare ps = psRepo.findByPostAndBootUser(p, userRepo.findOne(id));
				psRepo.delete(ps);
			}
		}
	}
	
	@Override
	public void setRelations(PostDto source, Iterable<Long> targetIds, String fieldName) {
		Post p = postRepo.findOne(source.getId());
		if ("sharedUsers".equals(fieldName)) {
			psRepo.findByPost(p, 0L, 10000L).forEach(ps -> psRepo.delete(ps)); 
			for(Long id : targetIds) {
				PostShare ps = new PostShare(p, userRepo.findOne(id));
				psRepo.save(ps);
			}
		}
	}
}
