package com.jianglibo.wx.katharsis.repository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jianglibo.wx.domain.BootUser;
import com.jianglibo.wx.domain.Role;
import com.jianglibo.wx.facade.BootUserFacadeRepository;
import com.jianglibo.wx.facade.RoleFacadeRepository;
import com.jianglibo.wx.katharsis.dto.RoleDto;
import com.jianglibo.wx.katharsis.dto.UserDto;

@Component
public class UserToRoleRepositoryImpl extends RelationshipRepositoryBaseMine<UserDto, RoleDto> {
	
	@Autowired
	private BootUserFacadeRepository userRepo;
	
	@Autowired
	private RoleFacadeRepository roleRepo;
	
	protected UserToRoleRepositoryImpl() {
		super(UserDto.class, RoleDto.class);
	}
	
	@Override
	public void addRelations(UserDto source, Iterable<Long> targetIds, String fieldName) {
		BootUser bu = userRepo.findOne(source.getId());
		if ("roles".equals(fieldName)) {
			Set<Role> roles = new HashSet<>(bu.getRoles());
			for(Long id : targetIds) {
				roles.add(roleRepo.findOne(id));
			}
			bu.setRoles(roles);
			userRepo.save(bu);
		}
	}
	
	@Override
	public void removeRelations(UserDto source, Iterable<Long> targetIds, String fieldName) {
		BootUser bu = userRepo.findOne(source.getId());
		if ("roles".equals(fieldName)) {
			Set<Role> roles = new HashSet<>(bu.getRoles());
			for(Long id : targetIds) {
				Optional<Role> ro = roles.stream().filter(r -> r.getId().equals(id)).findAny();
				if (ro.isPresent()) {
					roles.remove(ro.get());
				}
			}
			bu.setRoles(roles);
			userRepo.save(bu);
		}
	}

}
