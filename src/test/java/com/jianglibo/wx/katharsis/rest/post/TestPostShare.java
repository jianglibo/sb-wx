package com.jianglibo.wx.katharsis.rest.post;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.jianglibo.wx.JsonApiListBodyWrapper;
import com.jianglibo.wx.KatharsisBase;
import com.jianglibo.wx.config.JsonApiResourceNames;
import com.jianglibo.wx.domain.BootUser;
import com.jianglibo.wx.domain.Post;
import com.jianglibo.wx.katharsis.dto.UserDto;
import com.jianglibo.wx.repository.PostRepository;

public class TestPostShare  extends KatharsisBase {
	
	@Autowired
	private PostRepository postRepo;
	
	private String jwtToken;
	
	@Before
	public void b() throws JsonParseException, JsonMappingException, IOException {
		deleteAllPost();
		initTestUser();
	}
	
	@Test
	public void tShareByRelation() throws JsonParseException, JsonMappingException, IOException {
		BootUser bu = bootUserRepo.findByName("admin");
		
		Post post = new Post();
		post.setTitle("title");
		post.setContent("content");
		post.setCreator(bu);
		postRepo.save(post);
		
		JsonApiListBodyWrapper jbw = new JsonApiListBodyWrapper("users", user1.getId(), user2.getId());
		String body = indentOm.writeValueAsString(jbw);
		writeDto(body, getResourceName(), "altersharedusers");
		response = addRelationWithContent(body, "sharedUsers", post.getId(), jwtToken);
		assertResponseCode(response, 204);
		
		response = requestForBody(jwtToken, getItemUrl(post.getId()) + "/sharedUsers");
		assertItemNumber(response, UserDto.class, 2);
		
		jbw = new JsonApiListBodyWrapper("users", user1.getId());
		body = indentOm.writeValueAsString(jbw);
				
		response = deleteRelationWithContent(body, "sharedUsers", post.getId(), jwtToken);
		response = requestForBody(jwtToken, getItemUrl(post.getId()) + "/sharedUsers");
		assertItemNumber(response, UserDto.class, 1);
		
		jbw = new JsonApiListBodyWrapper("users", new ArrayList<>());
		body = indentOm.writeValueAsString(jbw);
		response = replaceRelationWithContent(body, "sharedUsers", post.getId(), jwtToken);
		response = requestForBody(jwtToken, getItemUrl(post.getId()) + "/sharedUsers");
		assertItemNumber(response, UserDto.class, 0);
	}


	@Override
	protected String getResourceName() {
		return JsonApiResourceNames.POST;
	}
}
