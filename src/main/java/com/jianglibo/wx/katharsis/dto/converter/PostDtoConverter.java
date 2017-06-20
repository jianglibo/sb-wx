package com.jianglibo.wx.katharsis.dto.converter;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jianglibo.wx.domain.Medium;
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

//	@Override
//	public Post dto2Entity(PostDto dto) {
//		Post entity = new Post();
//		BeanUtils.copyProperties(dto, entity, "creator", "media");
//		dto.getMedia().stream().map(mdto -> {
//			Medium m = mediumRepo.findOne(mdto.getId());
//			return m;
//			});
//		return entity;
//	}

	@Override
	public PostDto entity2Dto(Post entity) {
		PostDto dto = new PostDto();
		BeanUtils.copyProperties(entity, dto, "creator", "media");
		dto.setCreator(userConverter.entity2Dto(entity.getCreator()));
		entity.getMedia().forEach(m -> {
			dto.getMedia().add(mediumConverter.entity2Dto(m));
		});
		return dto;
	}

}
