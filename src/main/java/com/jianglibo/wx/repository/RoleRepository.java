package com.jianglibo.wx.repository;

import com.jianglibo.wx.domain.Role;

public interface RoleRepository extends RepositoryBase<Role> {
    Role findByName(String rn);

}
