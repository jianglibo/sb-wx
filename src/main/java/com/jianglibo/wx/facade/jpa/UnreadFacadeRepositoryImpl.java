package com.jianglibo.wx.facade.jpa;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jianglibo.wx.domain.BootUser;
import com.jianglibo.wx.domain.Unread;
import com.jianglibo.wx.facade.SortBroker;
import com.jianglibo.wx.facade.UnreadFacadeRepository;
import com.jianglibo.wx.katharsis.dto.UnreadDto;
import com.jianglibo.wx.repository.UnreadRepository;

/**
 * @author jianglibo@gmail.com
 *
 */
@Component
public class UnreadFacadeRepositoryImpl extends FacadeRepositoryBaseImpl<Unread,UnreadDto, UnreadRepository> implements UnreadFacadeRepository {
	
	@Autowired
	public UnreadFacadeRepositoryImpl(UnreadRepository jpaRepo) {
		super(jpaRepo);
	}
	
	@Override
	public Unread save(Unread entity, UnreadDto dto) {
		return super.save(entity, dto);
	}
	

	@Override
	public List<Unread> findAll() {
		return getRepository().findAll();
	}

	@Override
	public Unread newByDto(UnreadDto dto) {
		return null;
	}

	@Override
	public List<Unread> findByBootUserAndType(BootUser user, String type, long offset, Long limit,
			SortBroker... sortBrokers) {
		return getRepository().findByBootUserAndType(user, type, new SimplePageable(offset, limit, sortBrokers));
	}

	@Override
	public long countByBootUserAndType(BootUser user, String type) {
		return getRepository().countByBootUserAndType(user, type);
	}
}
