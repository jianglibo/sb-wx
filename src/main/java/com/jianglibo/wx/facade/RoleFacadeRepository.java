package com.jianglibo.wx.facade;

import java.util.List;

import com.jianglibo.wx.domain.Role;
import com.jianglibo.wx.katharsis.dto.RoleDto;


public interface RoleFacadeRepository extends FacadeRepositoryBase<Role, RoleDto> {
    Role findByName(String rn);
    Role initSave(Role entity);
    List<Role> findAll();
}
