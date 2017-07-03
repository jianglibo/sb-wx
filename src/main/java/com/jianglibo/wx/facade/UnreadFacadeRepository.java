package com.jianglibo.wx.facade;

import java.util.List;

import com.jianglibo.wx.domain.BootUser;
import com.jianglibo.wx.domain.Unread;
import com.jianglibo.wx.katharsis.dto.UnreadDto;


public interface UnreadFacadeRepository extends FacadeRepositoryBase<Unread, UnreadDto> {
    Page<Unread> findByBootUserAndType(BootUser user, String type, PageFacade pf);
    
    List<Unread> findAll();
    boolean userHasReadThisPost(BootUser user, Long id);
}
