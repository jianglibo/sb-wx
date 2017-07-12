package com.jianglibo.wx.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.jianglibo.wx.domain.BootUser;
import com.jianglibo.wx.domain.Unread;

public interface UnreadRepository extends RepositoryBase<Unread> {
    Page<Unread> findByBootUserAndType(BootUser user, String type, Pageable pageable);
	Unread findByBootUserAndTypeAndObid(BootUser user, String type, Long id);
}
