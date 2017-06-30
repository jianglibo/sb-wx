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
import com.jianglibo.wx.katharsis.dto.PostDto;
import com.jianglibo.wx.repository.UnreadRepository;

public class TestPostGetList  extends KatharsisBase {
	
	@Autowired
	private UnreadRepository unreadRepo;
	
	@Before
	public void b() throws JsonParseException, JsonMappingException, IOException {
		deleteAllPost();
		groupRepo.deleteAll();
		unreadRepo.deleteAll();
		jwtToken = getAdminJwtToken();
	}
	
	@Test
	public void tGetList() throws IOException {
		BootUser b1 = tutil.createBootUser("b1", "123");
		createPost(b1, true);
		String jwt = getJwtToken("b1", "123");
		response = requestForBody(jwt, getBaseURI() + "?filter[toAll]=true&page[offset]=0&page[limit]=19");
		List<PostDto> posts = getList(response, PostDto.class);
		assertThat(posts.size(), equalTo(1));
		
		response = requestForBody(jwt, getBaseURI() + "?filter[toAll]=true&page[offset]=19&page[limit]=19");
		posts = getList(response, PostDto.class);
		assertThat(posts.size(), equalTo(0));

		
		response = requestForBody(jwt, getBaseURI() + "?filter[toAll]=false");
		assertAccessDenied(response);
	}
	
	@Test
	public void tGetListNone() throws IOException {
		BootUser b1 = tutil.createBootUser("b1", "123");
		createPost(b1, false);
		String jwt = getJwtToken("b1", "123");
		response = requestForBody(jwt, getBaseURI());
		List<PostDto> posts = getList(response, PostDto.class);
		assertThat(posts.size(), equalTo(0));
	}

	@Override
	protected String getResourceName() {
		return JsonApiResourceNames.POST;
	}
}
