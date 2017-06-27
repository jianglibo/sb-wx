package com.jianglibo.wx.katharsis.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jianglibo.wx.domain.BootGroup;
import com.jianglibo.wx.domain.GroupUserRelation;
import com.jianglibo.wx.domain.Post;
import com.jianglibo.wx.domain.PostShare;
import com.jianglibo.wx.facade.BootGroupFacadeRepository;
import com.jianglibo.wx.facade.GroupUserRelationFacadeRepository;
import com.jianglibo.wx.facade.PostFacadeRepository;
import com.jianglibo.wx.facade.PostShareFacadeRepository;
import com.jianglibo.wx.katharsis.dto.GroupDto;
import com.jianglibo.wx.katharsis.dto.PostDto;
import com.jianglibo.wx.katharsis.exception.UnsupportedRequestException;

@Component
public class PostToGroupRepositoryImpl extends RelationshipRepositoryBaseMine<PostDto, GroupDto> {
	
	@Autowired
	private PostFacadeRepository postRepo;
	
	@Autowired
	private BootGroupFacadeRepository groupRepo;
	
	@Autowired
	private GroupUserRelationFacadeRepository guRepo;
	
	@Autowired
	private PostShareFacadeRepository psRepo;

	protected PostToGroupRepositoryImpl() {
		super(PostDto.class, GroupDto.class);
	}
	
	@Override
	public void addRelations(PostDto source, Iterable<Long> targetIds, String fieldName) {
		Post p = postRepo.findOne(source.getId());
		if ("sharedGroups".equals(fieldName)) {
			for(Long id : targetIds) {
				BootGroup bg = groupRepo.findOne(id);
				List<GroupUserRelation> gurs =  guRepo.findByBootGroup(bg);
				gurs.stream().map(gur -> gur.getBootUser()).forEach(user -> {
					PostShare ps = new PostShare(p, user);
					psRepo.save(ps);
				});
			}
		}
	}
	
	@Override
	public void removeRelations(PostDto source, Iterable<Long> targetIds, String fieldName) {
		throw new UnsupportedRequestException(String.format("post doesn't support remove relation: %s", fieldName));
	}

}
