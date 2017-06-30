package com.jianglibo.wx.katharsis.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jianglibo.wx.domain.Approve;
import com.jianglibo.wx.domain.BootGroup;
import com.jianglibo.wx.domain.BootUser;
import com.jianglibo.wx.domain.GroupUserRelation;
import com.jianglibo.wx.facade.ApproveFacadeRepository;
import com.jianglibo.wx.facade.BootGroupFacadeRepository;
import com.jianglibo.wx.facade.BootUserFacadeRepository;
import com.jianglibo.wx.facade.GroupUserRelationFacadeRepository;
import com.jianglibo.wx.katharsis.dto.GroupDto;
import com.jianglibo.wx.katharsis.dto.UserDto;

@Component
public class UserToGroupRepositoryImpl extends RelationshipRepositoryBaseMine<UserDto, GroupDto> {
	
	@Autowired
	private BootUserFacadeRepository userRepo;
	
	@Autowired
	private BootGroupFacadeRepository groupRepo;

	@Autowired
	private ApproveFacadeRepository approveRepo;
	
	@Autowired
	private GroupUserRelationFacadeRepository guRepo;
	
	protected UserToGroupRepositoryImpl() {
		super(UserDto.class, GroupDto.class);
	}
	
	/**
	 * send a request to group owner. 
	 */
	@Override
	public void addRelations(UserDto source, Iterable<Long> targetIds, String fieldName) {
		BootUser bu = userRepo.findOne(source.getId(), true);
		if ("joinedGroups".equals(fieldName)) {
			for(Long id : targetIds) {
				BootGroup gp = groupRepo.findOne(id, true);
				if (gp.isOpenToAll()) {
					GroupUserRelation gur = new GroupUserRelation(gp, bu);
					guRepo.save(gur, null);
				} else {
					Approve approve = new Approve.ApproveBuilder<>(gp).sender(bu).receiver(gp.getCreator()).build();
					approveRepo.save(approve, null);
				}
			}
		}
	}
	
	/**
	 * leave the group directly.
	 */
	@Override
	public void removeRelations(UserDto source, Iterable<Long> targetIds, String fieldName) {
		BootUser bu = userRepo.findOne(source.getId(), true);
		if ("joinedGroups".equals(fieldName)) {
			for(Long id : targetIds) {
				BootGroup gp = groupRepo.findOne(id, true);
				GroupUserRelation gur = guRepo.findByBootGroupAndBootUser(gp, bu);
				if (gur != null) {
					guRepo.delete(gur);
				}
			}
		}
	}
}
