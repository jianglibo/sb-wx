package com.jianglibo.wx.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.jianglibo.wx.domain.Approve;
import com.jianglibo.wx.domain.BootUser;

public interface ApproveRepository extends RepositoryBase<Approve> {

	List<Approve> findAllByRequester(BootUser user, Pageable simplePageable);

	long countByRequester(BootUser user);

	List<Approve> findAllByReceiver(BootUser user, Pageable simplePageable);

	long countByReceiver(BootUser user);

}
