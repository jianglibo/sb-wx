package com.jianglibo.wx.katharsis.repository;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import com.jianglibo.wx.domain.Approve;
import com.jianglibo.wx.domain.BootGroup;
import com.jianglibo.wx.domain.BootUser;
import com.jianglibo.wx.domain.GroupUserRelation;
import com.jianglibo.wx.facade.ApproveFacadeRepository;
import com.jianglibo.wx.facade.BootGroupFacadeRepository;
import com.jianglibo.wx.facade.BootUserFacadeRepository;
import com.jianglibo.wx.facade.GroupUserRelationFacadeRepository;
import com.jianglibo.wx.facade.Page;
import com.jianglibo.wx.katharsis.dto.ApproveDto;
import com.jianglibo.wx.katharsis.dto.UserDto;
import com.jianglibo.wx.katharsis.dto.converter.ApproveDtoConverter;
import com.jianglibo.wx.katharsis.dto.converter.DtoConverter.Scenario;
import com.jianglibo.wx.katharsis.exception.UnsupportedRequestException;
import com.jianglibo.wx.katharsis.repository.ApproveDtoRepository.ApproveDtoList;
import com.jianglibo.wx.util.PropertyCopyUtil;
import com.jianglibo.wx.util.QuerySpecUtil;
import com.jianglibo.wx.util.QuerySpecUtil.RelationQuery;
import com.jianglibo.wx.util.SecurityUtil;

import io.katharsis.queryspec.QuerySpec;

@Component
public class ApproveDtoRepositoryImpl  extends DtoRepositoryBase<ApproveDto, ApproveDtoList, Approve, ApproveFacadeRepository> implements ApproveDtoRepository {
	
	@Autowired
	private BootUserFacadeRepository userRepo;
	
	@Autowired
	private BootGroupFacadeRepository groupRepo;
	
	@Autowired
	private GroupUserRelationFacadeRepository guRepo;
	
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
	public ApproveDto modify(ApproveDto dto) {
		Approve entity = getRepository().findOne(dto.getId(), false);
		PropertyCopyUtil.applyPatch(entity,dto);
		
		if (BootGroup.class.getName().equals(entity.getTargetType())) {
			BootGroup group = groupRepo.findOne(entity.getTargetId(), true);
			if (group.getCreator().getId().equals(SecurityUtil.getLoginUserId())) { // if group's creator isn't the current user, reject it.
				switch (entity.getState()) {
				case APPROVED:
					GroupUserRelation gur = new GroupUserRelation(group, entity.getRequester());
					guRepo.save(gur, null);
					break;
				case REJECT:
					break;
				default:
					break;
				}
			} else {
				throw new AccessDeniedException("group is not created by you.");
			}
		}
		return getConverter().entity2Dto(saveToBackendRepo(dto, entity), Scenario.MODIFY);
	}

	@Override
	protected ApproveDtoList findWithRelationAndSpec(RelationQuery rq, QuerySpec querySpec) {
		if ("receiver".equals(rq.getRelationName())) {
			UserDto userDto = new UserDto(rq.getRelationIds().get(0));
			BootUser bu = userRepo.findOne(userDto.getId(), true);
			Page<Approve> approves =  getRepository().findReceived(bu, QuerySpecUtil.getPageFacade(querySpec));
			ApproveDtoList adl = convertToResourceList(approves, Scenario.RELATION_LIST);
			adl.forEach(a -> a.setReceiver(userDto));
			return adl;
		} else if ("requester".equals(rq.getRelationName())) {
			UserDto userDto = new UserDto(rq.getRelationIds().get(0));
			BootUser bu = userRepo.findOne(userDto.getId(), true);
			Page<Approve> approves =  getRepository().findSent(bu, QuerySpecUtil.getPageFacade(querySpec));
			ApproveDtoList adl = convertToResourceList(approves, Scenario.RELATION_LIST);
			adl.forEach(a -> a.setRequester(userDto));
			return adl;
		}
		return null;
	}
}
