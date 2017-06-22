package com.jianglibo.wx.katharsis.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jianglibo.wx.domain.BootUser;
import com.jianglibo.wx.domain.FollowRelation;
import com.jianglibo.wx.facade.BootUserFacadeRepository;
import com.jianglibo.wx.facade.FollowRelationFacadeRepository;
import com.jianglibo.wx.katharsis.dto.UserDto;

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
	public void addRelations(UserDto source, Iterable<Long> targetIds, String fieldName) {
		BootUser befollowed = userRepo.findOne(source.getId());
		for(Long id : targetIds) {
			FollowRelation fr = new FollowRelation(userRepo.findOne(id), befollowed);
			frfRepo.save(fr);
		}
	}
	
	@Override
	public void removeRelations(UserDto source, Iterable<Long> targetIds, String fieldName) {
		BootUser befollowed = userRepo.findOne(source.getId());
		BootUser follower;
		FollowRelation fr;
		for(Long id : targetIds) {
			follower = userRepo.findOne(id);
			fr = frfRepo.findByFollowedAndFollower(befollowed, follower);
			frfRepo.delete(fr);
		}
	}

}
