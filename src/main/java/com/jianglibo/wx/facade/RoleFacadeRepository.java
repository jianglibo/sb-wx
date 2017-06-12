package com.jianglibo.wx.facade;

import com.jianglibo.wx.domain.Role;


public interface RoleFacadeRepository extends FacadeRepositoryBase<Role> {
    Role findByName(String rn);
    
    Role initSave(Role entity);
}
