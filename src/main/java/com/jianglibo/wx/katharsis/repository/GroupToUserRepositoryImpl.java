package com.jianglibo.wx.katharsis.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jianglibo.wx.katharsis.dto.UserDto;
import com.jianglibo.wx.domain.BootGroup;
import com.jianglibo.wx.domain.GroupUserRelation;
import com.jianglibo.wx.facade.BootGroupFacadeRepository;
import com.jianglibo.wx.facade.BootUserFacadeRepository;
import com.jianglibo.wx.facade.GroupUserRelationFacadeRepository;
import com.jianglibo.wx.katharsis.dto.GroupDto;

import io.katharsis.repository.RelationshipRepositoryBase;

@Component
public class GroupToUserRepositoryImpl extends RelationshipRepositoryBase<GroupDto, Long, UserDto, Long> {

	@Autowired
	private BootUserFacadeRepository userRepo;
	
	@Autowired
	private BootGroupFacadeRepository groupRepo;
	
	@Autowired
	private GroupUserRelationFacadeRepository gurRepo;
	
	protected GroupToUserRepositoryImpl() {
		super(GroupDto.class, UserDto.class);
	}
	
	@Override
	public void addRelations(GroupDto source, Iterable<Long> targetIds, String fieldName) {
		BootGroup gp = groupRepo.findOne(source.getId());
		GroupUserRelation gur;
		for (Long target : targetIds) {
			gur = new GroupUserRelation(gp, userRepo.findOne(target));
			gurRepo.save(gur);
		}
	}
	
	@Override
	public void removeRelations(GroupDto source, Iterable<Long> targetIds, String fieldName) {
		BootGroup gp = groupRepo.findOne(source.getId());
		GroupUserRelation gur;
		for (Long target : targetIds) {
			gur = gurRepo.findByBootGroupAndBootUser(gp, userRepo.findOne(target));
			gurRepo.delete(gur);
		}
	}

}
