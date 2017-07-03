package com.jianglibo.wx.facade.jpa;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jianglibo.wx.domain.BootUser;
import com.jianglibo.wx.domain.Post;
import com.jianglibo.wx.domain.Unread;
import com.jianglibo.wx.facade.Page;
import com.jianglibo.wx.facade.PageFacade;
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
	public Page<Unread> findByBootUserAndType(BootUser user, String type, PageFacade pf) {
		org.springframework.data.domain.Page<Unread> opage = getRepository().findByBootUserAndType(user, type, new SimplePageable(pf)); 
		return new Page<>(opage.getTotalElements(), opage.getContent());
	}

	public Unread findByBootUserAndTypeAndObidAndRead(BootUser user, String type, Long id, boolean read) {
		return getRepository().findByBootUserAndTypeAndObidAndRead(user,type,id, read);
	}

	@Override
	public boolean userHasReadThisPost(BootUser user, Long id) {
		Unread ur = getRepository().findByBootUserAndTypeAndObidAndRead(user,Post.class.getSimpleName(),id, true);
		return ur != null;
	}
}
