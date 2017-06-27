package com.jianglibo.wx.katharsis.repository;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jianglibo.wx.domain.Approve;
import com.jianglibo.wx.domain.BootUser;
import com.jianglibo.wx.facade.ApproveFacadeRepository;
import com.jianglibo.wx.facade.BootUserFacadeRepository;
import com.jianglibo.wx.katharsis.dto.ApproveDto;
import com.jianglibo.wx.katharsis.dto.UserDto;
import com.jianglibo.wx.katharsis.dto.converter.ApproveDtoConverter;
import com.jianglibo.wx.katharsis.dto.converter.DtoConverter.Scenario;
import com.jianglibo.wx.katharsis.exception.UnsupportedRequestException;
import com.jianglibo.wx.katharsis.repository.ApproveDtoRepository.ApproveDtoList;
import com.jianglibo.wx.util.QuerySpecUtil;
import com.jianglibo.wx.util.QuerySpecUtil.RelationQuery;

import io.katharsis.queryspec.QuerySpec;

@Component
public class ApproveDtoRepositoryImpl  extends DtoRepositoryBase<ApproveDto, ApproveDtoList, Approve, ApproveFacadeRepository> implements ApproveDtoRepository {
	
	@Autowired
	private BootUserFacadeRepository userRepo;
	
	@Autowired
	public ApproveDtoRepositoryImpl(ApproveFacadeRepository repository, ApproveDtoConverter converter) {
		super(ApproveDto.class, ApproveDtoList.class, Approve.class, repository, converter);
	}

	@Override
	protected ApproveDtoList findAllWithQuerySpec(QuerySpec querySpec) {
		return null;
	}

	@Override
	protected List<String> checkAllSortableFieldAllowed(QuerySpec querySpec) {
		return null;
	}
	
	@Override
	public ApproveDto createNew(ApproveDto dto) {
		throw new UnsupportedRequestException("Approve object direct creation is forbiden.");
	}

	@Override
	protected ApproveDtoList findWithRelationAndSpec(RelationQuery rq, QuerySpec querySpec) {
		if ("receiver".equals(rq.getRelationName())) {
			UserDto userDto = new UserDto();
			userDto.setId(rq.getRelationIds().get(0));
			BootUser bu = userRepo.findOne(userDto.getId());
			List<Approve> approves =  getRepository().findReceived(bu, querySpec.getOffset(), querySpec.getLimit(), QuerySpecUtil.getSortBrokers(querySpec));
			long count = getRepository().countReceived(bu);
			ApproveDtoList adl = convertToResourceList(approves, count, Scenario.RELATION_LIST);
			adl.forEach(a -> a.setReceiver(userDto));
			return adl;
		} else if ("requester".equals(rq.getRelationName())) {
			UserDto userDto = new UserDto();
			userDto.setId(rq.getRelationIds().get(0));
			BootUser bu = userRepo.findOne(userDto.getId());
			List<Approve> approves =  getRepository().findSent(bu, querySpec.getOffset(), querySpec.getLimit(), QuerySpecUtil.getSortBrokers(querySpec));
			long count = getRepository().countSend(bu);
			ApproveDtoList adl = convertToResourceList(approves, count, Scenario.RELATION_LIST);
			adl.forEach(a -> a.setRequester(userDto));
			return adl;
		}
		return null;
	}
}
