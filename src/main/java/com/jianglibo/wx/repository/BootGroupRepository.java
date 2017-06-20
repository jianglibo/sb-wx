package com.jianglibo.wx.repository;

import com.jianglibo.wx.domain.BootGroup;

public interface BootGroupRepository extends RepositoryBase<BootGroup> {
    BootGroup findByName(String rn);

}
