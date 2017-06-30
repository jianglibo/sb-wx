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
	}
	
	@Test
	public void tAddOne() throws JsonParseException, JsonMappingException, IOException {
		BootUser user1 = tutil.createUser1();
		BootUser user2 = tutil.createUser2();
		
		Post post1 = new Post();
		post1.setTitle("title");
		post1.setContent("content");
		post1.setCreator(user2);
		post1 = postRepo.save(post1);
		
		Post post2 = new Post();
		post2.setTitle("title1");
		post2.setContent("content1");
		post2.setCreator(user2);
		post2 = postRepo.save(post2);
		psRepo.save(new PostShare(post1, user1));
		psRepo.save(new PostShare(post2, user1));
		
		String jwt1 = getJwtToken(tutil.USER_1, tutil.PASSWORD);
		response = requestForBody(jwt1, getItemUrl(user1.getId()) + "/receivedPosts");
		
		writeDto(response.getBody(), getResourceName(), "getreceivedpostsrelation");
		List<PostDto> posts = getList(response, PostDto.class);
		assertThat(posts.size(), equalTo(2));
		
		String jwt2 = getJwtToken(tutil.USER_2, tutil.PASSWORD);
		response = requestForBody(jwt2, getItemUrl(user1.getId()) + "/receivedPosts");
		assertAccessDenied(response);
		
		// creator is permitted.
		response = requestForBody(jwt2, getItemUrl(JsonApiResourceNames.POST, posts.get(0).getId()));
		assertData(response);
		
		// shared users are permitted.
		response = requestForBody(jwt1, getItemUrl(JsonApiResourceNames.POST, posts.get(0).getId()));
		assertData(response);
		
		// other users are denied.		
		BootUser user3 = tutil.createBootUser("user3", "12345");
		String jwt3 = getJwtToken("user3", "12345");
		response = requestForBody(jwt3, getItemUrl(JsonApiResourceNames.POST, posts.get(0).getId()));
		assertAccessDenied(response);
	}

	@Override
	protected String getResourceName() {
		return JsonApiResourceNames.BOOT_USER;
	}

}
