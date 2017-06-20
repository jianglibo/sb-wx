package com.jianglibo.wx.katharsis.rest;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.jianglibo.wx.KatharsisBase;
import com.jianglibo.wx.config.JsonApiResourceNames;
import com.jianglibo.wx.jwt.JwtUtil;
import com.jianglibo.wx.katharsis.dto.MediumDto;
import com.jianglibo.wx.katharsis.dto.PostDto;
import com.jianglibo.wx.webapp.authorization.FileUploadFilter.FileUploadResponse;

public class TestPostApi  extends KatharsisBase {
	
	
	private String jwtToken;
	
	@Before
	public void b() throws JsonParseException, JsonMappingException, IOException {
		deleteAllPost();
		jwtToken = getAdminJwtToken();
	}
	
	@Test
	public void tAdmionAddOne() throws JsonParseException, JsonMappingException, IOException {
		
		FileUploadResponse fr = uploadFile(Paths.get("fixturesingit", "v.js"));
		MediumDto m = fr.getMedia().get(0);
		String s = getFixture(JsonApiResourceNames.POST);
		s = replaceEmbeddedListIdReturnString(s, "media", m.getId());
		ResponseEntity<String> response = postItemWithContent(s, jwtToken);
		response.getHeaders().containsKey(JwtUtil.REFRESH_HEADER_NAME);
		writeDto(response, getResourceName(), ActionNames.POST_RESULT);
		PostDto newPost = getOne(response.getBody(), PostDto.class);
		assertThat(newPost.getTitle(), equalTo("title"));
		
		assertThat(newPost.getMedia().size(), equalTo(1));
		assertThat(newPost.getMedia().get(0).getId(), equalTo(m.getId()));
		
		response = requestForBody(jwtToken, getBaseURI());
		writeDto(response, getResourceName(), ActionNames.GET_LIST);
		response = requestForBody(jwtToken, getItemUrl(newPost.getId()));
		writeDto(response, getResourceName(), ActionNames.GET_ONE);
		deleteByExchange(jwtToken, getItemUrl(newPost.getId()));
	}


	@Override
	protected String getResourceName() {
		return JsonApiResourceNames.POST;
	}

}
