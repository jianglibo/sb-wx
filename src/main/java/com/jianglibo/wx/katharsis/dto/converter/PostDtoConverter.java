package com.jianglibo.wx.katharsis.dto.converter;

import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jianglibo.wx.domain.Post;
import com.jianglibo.wx.facade.MediumFacadeRepository;
import com.jianglibo.wx.katharsis.dto.PostDto;

@Component
public class PostDtoConverter implements DtoConverter<Post, PostDto> {
	
	@Autowired
	private UserDtoConverter userConverter;
	
	@Autowired
	private MediumFacadeRepository mediumRepo;
	
	@Autowired
	private MediumDtoConverter mediumConverter;

	@Override
	public PostDto entity2Dto(Post entity, Scenario scenario) {
		PostDto dto = new PostDto();
		BeanUtils.copyProperties(entity, dto, "creator", "media");
		dto.setCreator(userConverter.entity2Dto(entity.getCreator(), scenario));
		if (entity.getMedia() != null) {
			dto.setMedia(entity.getMedia().stream().map(en -> mediumConverter.entity2Dto(en, Scenario.RELATION_LIST)).collect(Collectors.toList()));
		}
		if (scenario == Scenario.FIND_ONE) {
			dto.setRead(true);
		}
		return dto;
	}

}
