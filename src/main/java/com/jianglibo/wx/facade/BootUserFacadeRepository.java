package com.jianglibo.wx.facade;

import java.util.List;

import com.jianglibo.wx.domain.BootUser;
import com.jianglibo.wx.katharsis.dto.UserDto;

public interface BootUserFacadeRepository extends FacadeRepositoryBase<BootUser, UserDto> {

	BootUser findByEmail(String emailOrMobile);

	BootUser findByMobile(String emailOrMobile);

	BootUser findByName(String emailOrMobile);
	
	BootUser updatePassword(Long id, String encodedPassword);
	
	BootUser patch(BootUser user, UserDto userDto);
	
	BootUser findByOpenId(String openId);
	
	List<BootUser> findAllByGroup(long groupId, long offset, Long limit, SortBroker...sortBrokers);
	
	long countByGroup(long groupId);
}
