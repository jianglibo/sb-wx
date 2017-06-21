package com.jianglibo.wx.repository;

import org.springframework.data.repository.query.Param;

import com.jianglibo.wx.domain.BootUser;


public interface BootUserRepository extends RepositoryBase<BootUser> {

    BootUser findByEmail(@Param("email") String email);

    BootUser findByMobile(@Param("mobile") String mobile);

    BootUser findByName(@Param("name") String name);
    
    BootUser findByOpenId(@Param("openId") String openId);
}
