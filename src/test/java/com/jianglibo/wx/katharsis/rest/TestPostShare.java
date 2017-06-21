package com.jianglibo.wx.katharsis.rest;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.jianglibo.wx.KatharsisBase;
import com.jianglibo.wx.KatharsisBase.JsonApiBodyWrapper;
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
		jwtToken = getAdminJwtToken();
	}
	
	@Test
	public void tAdmionAddOne() throws JsonParseException, JsonMappingException, IOException {
		BootUser bu = bootUserRepo.findByName("admin");
		
		Post post = new Post();
		post.setTitle("title");
		post.setContent("content");
		post.setCreator(bu);
		postRepo.save(post);
		BootUser b1 = createBootUser("b1", "123");
		
		JsonApiBodyWrapper jbw = new JsonApiBodyWrapper("users", b1.getId());
		response = addRelationWithContent(indentOm.writeValueAsString(jbw), "sharedUsers", post.getId(), jwtToken);
		assertResponseCode(response, 204);
		
		response = requestForBody(jwtToken, getItemUrl(post.getId()) + "/sharedUsers");
		assertItemNumber(response, UserDto.class, 1);

	}


	@Override
	protected String getResourceName() {
		return JsonApiResourceNames.POST;
	}
}
