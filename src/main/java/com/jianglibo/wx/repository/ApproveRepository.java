package com.jianglibo.wx.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.jianglibo.wx.domain.Approve;
import com.jianglibo.wx.domain.BootUser;

public interface ApproveRepository extends RepositoryBase<Approve> {

	Page<Approve> findAllByRequester(BootUser user, Pageable simplePageable);

	Page<Approve> findAllByReceiver(BootUser user, Pageable simplePageable);

}
