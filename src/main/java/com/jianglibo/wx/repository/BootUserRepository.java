package com.jianglibo.wx.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import com.jianglibo.wx.domain.BootGroup;
import com.jianglibo.wx.domain.BootUser;


public interface BootUserRepository extends RepositoryBase<BootUser> {

    BootUser findByEmail(@Param("email") String email);

    BootUser findByMobile(@Param("mobile") String mobile);

    BootUser findByName(@Param("name") String name);
    
    BootUser findByOpenId(@Param("openId") String openId);
    
    List<BootUser> findAllByBootGroupsIn(List<BootGroup> groups, Pageable pageable);
    
    long countByBootGroupsIn(List<BootGroup> groups);

}
