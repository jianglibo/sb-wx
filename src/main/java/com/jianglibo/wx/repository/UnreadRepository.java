package com.jianglibo.wx.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.jianglibo.wx.domain.BootUser;
import com.jianglibo.wx.domain.Unread;

public interface UnreadRepository extends RepositoryBase<Unread> {
    List<Unread> findByBootUserAndType(BootUser user, String type, Pageable pageable);
    long countByBootUserAndType(BootUser user, String type);
	Unread findByBootUserAndTypeAndObid(BootUser user, String type, Long id);
}
