package com.jianglibo.wx.katharsis.repository;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jianglibo.wx.domain.BootUser;
import com.jianglibo.wx.domain.Unread;
import com.jianglibo.wx.facade.BootUserFacadeRepository;
import com.jianglibo.wx.facade.Page;
import com.jianglibo.wx.facade.UnreadFacadeRepository;
import com.jianglibo.wx.katharsis.dto.UnreadDto;
import com.jianglibo.wx.katharsis.dto.UserDto;
import com.jianglibo.wx.katharsis.dto.converter.DtoConverter.Scenario;
import com.jianglibo.wx.katharsis.dto.converter.UnreadDtoConverter;
import com.jianglibo.wx.katharsis.repository.UnreadDtoRepository.UnreadDtoList;
import com.jianglibo.wx.util.QuerySpecUtil;
import com.jianglibo.wx.util.QuerySpecUtil.RelationQuery;

import io.katharsis.queryspec.FilterSpec;
import io.katharsis.queryspec.QuerySpec;

@Component
public class UnreadDtoRepositoryImpl  extends DtoRepositoryBase<UnreadDto, UnreadDtoList, Unread, UnreadFacadeRepository> implements UnreadDtoRepository {
	
	@Autowired
	private BootUserFacadeRepository userRepo;
	
	@Autowired
	private UnreadFacadeRepository unreadRepo;
	
	@Autowired
	public UnreadDtoRepositoryImpl(UnreadFacadeRepository repository, UnreadDtoConverter converter) {
		super(UnreadDto.class, UnreadDtoList.class, Unread.class, repository, converter);
	}

	@Override
	protected UnreadDtoList findAllWithQuerySpec(QuerySpec querySpec) {
		return null;
	}

	@Override
	protected List<String> checkAllSortableFieldAllowed(QuerySpec querySpec) {
		return null;
	}

	@Override
	protected UnreadDtoList findWithRelationAndSpec(RelationQuery rq, QuerySpec querySpec) {
		if ("user".equals(rq.getRelationName())) {
			String type = "";
			for(FilterSpec fs : querySpec.getFilters()) {
				List<String> fps = fs.getAttributePath();
				if (fps.size() == 1 && "type".equals(fps.get(0))) {
					type = fs.getValue().toString();
				}
			}
			UserDto udto = new UserDto(rq.getRelationIds().get(0));
			BootUser bu = userRepo.findOne(udto.getId(), true);
			Page<Unread> unreads = unreadRepo.findByBootUserAndType(bu, type, QuerySpecUtil.getPageFacade(querySpec));
			UnreadDtoList urdl = convertToResourceList(unreads, Scenario.FIND_LIST);
			urdl.forEach(mn -> mn.setUser(udto));
			return urdl;
		}
		throw getUnsupportRelationException("unread", rq.getRelationName());
	}
}
