package com.jianglibo.wx.katharsis.rest;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.jianglibo.wx.KatharsisBase;
import com.jianglibo.wx.config.JsonApiResourceNames;
import com.jianglibo.wx.domain.BootUser;
import com.jianglibo.wx.domain.Post;
import com.jianglibo.wx.domain.PostShare;
import com.jianglibo.wx.katharsis.dto.PostDto;
import com.jianglibo.wx.repository.PostShareRepository;

public class TestUserReceivedPostRelation  extends KatharsisBase {
	
	@Autowired
	private PostShareRepository psRepo;
	
	@Before
	public void b() throws JsonParseException, JsonMappingException, IOException {
		deleteAllUsers();
		jwtToken = getAdminJwtToken();
	}
	
	@Test
	public void tAddOne() throws JsonParseException, JsonMappingException, IOException {
		BootUser bu = createBootUser("b1", "123", "a", "b", "c");
		BootUser bu1 = createBootUser("b2", "123");
		
		Post post1 = new Post();
		post1.setTitle("title");
		post1.setContent("content");
		post1.setCreator(bu1);
		post1 = postRepo.save(post1);
		
		Post post2 = new Post();
		post2.setTitle("title1");
		post2.setContent("content1");
		post2.setCreator(bu1);
		post2 = postRepo.save(post2);
		psRepo.save(new PostShare(post1, bu));
		psRepo.save(new PostShare(post2, bu));
		
		response = requestForBody(jwtToken, getItemUrl(bu.getId()) + "/receivedPosts");
		
		writeDto(response.getBody(), getResourceName(), "getreceivedpostsrelation");
		List<PostDto> posts = getList(response, PostDto.class);
		assertThat(posts.size(), equalTo(2));
	}

	@Override
	protected String getResourceName() {
		return JsonApiResourceNames.BOOT_USER;
	}

}
