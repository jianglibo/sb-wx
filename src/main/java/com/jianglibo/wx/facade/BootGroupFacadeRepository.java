package com.jianglibo.wx.facade;

import java.util.List;

import com.jianglibo.wx.domain.BootGroup;
import com.jianglibo.wx.katharsis.dto.GroupDto;


public interface BootGroupFacadeRepository extends FacadeRepositoryBase<BootGroup, GroupDto> {
    BootGroup findByName(String rn);
    BootGroup initSave(BootGroup entity);
    List<BootGroup> findAll();
}
