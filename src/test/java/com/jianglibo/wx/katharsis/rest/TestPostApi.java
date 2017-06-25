package com.jianglibo.wx.katharsis.rest;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.nio.file.Paths;

import org.apache.http.HttpResponse;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.jianglibo.wx.JsonApiPostBodyWrapper;
import com.jianglibo.wx.JsonApiPostBodyWrapperBuilder;
import com.jianglibo.wx.KatharsisBase;
import com.jianglibo.wx.config.JsonApiResourceNames;
import com.jianglibo.wx.jwt.JwtUtil;
import com.jianglibo.wx.katharsis.dto.MediumDto;
import com.jianglibo.wx.katharsis.dto.PostDto;

public class TestPostApi  extends KatharsisBase {
	
	
	private String jwtToken;
	
	@Before
	public void b() throws JsonParseException, JsonMappingException, IOException {
		deleteAllPost();
		jwtToken = getAdminJwtToken();
	}
	
	@Test
	public void tAdmionAddOne() throws JsonParseException, JsonMappingException, IOException {
		
		HttpResponse apacheResponse = uploadFile(jwtToken, Paths.get("fixturesingit", "v.js"));
		String url = apacheResponse.getFirstHeader("location").getValue();
		response = requestForBody(jwtToken, url);
		
		MediumDto m = getList(response, MediumDto.class).get(0);
		
		JsonApiPostBodyWrapper jbw = new JsonApiPostBodyWrapperBuilder(getResourceName())
				.addAttributePair("title", "title")
				.addAttributePair("content", "content")
				.addRelation("media", JsonApiResourceNames.MEDIUM, m.getId()).build();
		
		
		String s = objectMapper.writeValueAsString(jbw);
		writeDto(s, getResourceName(), "postcontent");
		ResponseEntity<String> response = postItemWithContent(s, jwtToken);
		response.getHeaders().containsKey(JwtUtil.REFRESH_HEADER_NAME);
		writeDto(response, getResourceName(), ActionNames.POST_RESULT);
		PostDto newPost = getOne(response.getBody(), PostDto.class);
		assertThat(newPost.getTitle(), equalTo("title"));
		
//		assertThat(newPost.getMedia().size(), equalTo(1));
//		assertThat(newPost.getMedia().get(0).getId(), equalTo(m.getId()));
		
		// get all posts
		response = requestForBody(jwtToken, getBaseURI());
		writeDto(response, getResourceName(), ActionNames.GET_LIST);
		assertItemNumber(response, PostDto.class, 1);
		// get this post
		response = requestForBody(jwtToken, getItemUrl(newPost.getId()));
		writeDto(response, getResourceName(), ActionNames.GET_ONE);
		
		// get this post include media
		response = requestForBody(jwtToken, getItemUrl(newPost.getId()) + "?include=media");
		writeDto(response, getResourceName(), ActionNames.GET_ONE_INCLUDE);
		PostDto post = getOne(response, PostDto.class);
		assertThat(post.getMedia().size(), equalTo(2));
		
		deleteByExchange(jwtToken, getItemUrl(newPost.getId()));
	}


	@Override
	protected String getResourceName() {
		return JsonApiResourceNames.POST;
	}
}
