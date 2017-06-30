package com.jianglibo.wx.katharsis.repository;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jianglibo.wx.domain.Medium;
import com.jianglibo.wx.domain.Post;
import com.jianglibo.wx.facade.MediumFacadeRepository;
import com.jianglibo.wx.facade.PostFacadeRepository;
import com.jianglibo.wx.katharsis.dto.MediumDto;
import com.jianglibo.wx.katharsis.dto.PostDto;
import com.jianglibo.wx.katharsis.dto.converter.MediumDtoConverter;
import com.jianglibo.wx.katharsis.dto.converter.DtoConverter.Scenario;
import com.jianglibo.wx.katharsis.repository.MediumDtoRepository.MediumDtoList;
import com.jianglibo.wx.util.QuerySpecUtil.RelationQuery;

import io.katharsis.queryspec.QuerySpec;

@Component
public class MediumDtoRepositoryImpl  extends DtoRepositoryBase<MediumDto, MediumDtoList, Medium, MediumFacadeRepository> implements MediumDtoRepository {
	
	@Autowired
	private PostFacadeRepository postRepo;
	
	@Autowired
	public MediumDtoRepositoryImpl(MediumFacadeRepository repository, MediumDtoConverter converter) {
		super(MediumDto.class, MediumDtoList.class, Medium.class, repository, converter);
	}

	@Override
	protected MediumDtoList findAllWithQuerySpec(QuerySpec querySpec) {
		return null;
	}

	@Override
	protected List<String> checkAllSortableFieldAllowed(QuerySpec querySpec) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected MediumDtoList findWithRelationAndSpec(RelationQuery rq, QuerySpec querySpec) {
		if ("post".equals(rq.getRelationName())) {
			PostDto dto = new PostDto(rq.getRelationIds().get(0));
			Post entity = postRepo.findOne(dto.getId(), true);
			List<Medium> media = entity.getMedia();
			return convertToResourceList(media, media.size(), Scenario.RELATION_LIST);
		}
		return null;
	}
}
