package com.jianglibo.wx.katharsis.rest.post;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.jianglibo.wx.KatharsisBase;
import com.jianglibo.wx.config.JsonApiResourceNames;
import com.jianglibo.wx.katharsis.dto.PostDto;

public class TestPostGetList  extends KatharsisBase {
	
	@Before
	public void b() throws JsonParseException, JsonMappingException, IOException {
		initTestUser();
	}
	
	@Test
	public void tGetList() throws IOException {
		createPost(user1, true);
		response = requestForBody(jwt1, getBaseURI() + "?filter[toAll]=true&page[offset]=0&page[limit]=19");
		List<PostDto> posts = getList(response, PostDto.class);
		assertThat(posts.size(), equalTo(1));
		writeDto(response, getResourceName(), ActionNames.GET_LIST);
		
		response = requestForBody(jwt1, getBaseURI() + "?filter[toAll]=true&page[offset]=19&page[limit]=19");
		posts = getList(response, PostDto.class);
		assertThat(posts.size(), equalTo(0));

		
		response = requestForBody(jwt1, getBaseURI() + "?filter[toAll]=false");
		assertAccessDenied(response);
	}
	
	@Test
	public void tGetListNone() throws IOException {
		createPost(user0, false);
		response = requestForBody(jwt1, getBaseURI());
		List<PostDto> posts = getList(response, PostDto.class);
		assertThat(posts.size(), equalTo(0));
	}

	@Override
	protected String getResourceName() {
		return JsonApiResourceNames.POST;
	}
}
