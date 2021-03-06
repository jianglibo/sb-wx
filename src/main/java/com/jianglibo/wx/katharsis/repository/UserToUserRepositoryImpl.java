package com.jianglibo.wx.katharsis.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jianglibo.wx.domain.BootUser;
import com.jianglibo.wx.domain.FollowRelation;
import com.jianglibo.wx.facade.BootUserFacadeRepository;
import com.jianglibo.wx.facade.FollowRelationFacadeRepository;
import com.jianglibo.wx.katharsis.dto.UserDto;
import com.jianglibo.wx.katharsis.exception.UnsupportedRequestException;

@Component
public class UserToUserRepositoryImpl extends RelationshipRepositoryBaseMine<UserDto, UserDto> {
	
	@Autowired
	private FollowRelationFacadeRepository frfRepo;
	
	@Autowired
	private BootUserFacadeRepository userRepo;

	protected UserToUserRepositoryImpl() {
		super(UserDto.class, UserDto.class);
	}
	
	@Override
	public void addRelations(UserDto followerDto, Iterable<Long> targetIds, String fieldName) {
		if ("followers".equals(fieldName)) {
			throw new UnsupportedRequestException("user's followers cannot change directly");
		}
		BootUser follower = userRepo.findOne(followerDto.getId(), true);
		if ("followeds".equals(fieldName)) {
			for(Long id : targetIds) {
				FollowRelation fr = new FollowRelation(follower, userRepo.findOne(id, true));
				frfRepo.save(fr, null);
			}
		}
	}
	
	@Override
	public void removeRelations(UserDto followerDto, Iterable<Long> targetIds, String fieldName) {
		if ("followers".equals(fieldName)) {
			throw new UnsupportedRequestException("user's followers cannot change directly");
		}
		BootUser follower = userRepo.findOne(followerDto.getId(), true);
		if ("followeds".equals(fieldName)) {
			BootUser befollowed;
			FollowRelation fr;
			for(Long id : targetIds) {
				befollowed = userRepo.findOne(id, true);
				fr = frfRepo.findByFollowedAndFollower(befollowed, follower);
				frfRepo.delete(fr);
			}
		}
	}

}
