package com.jianglibo.wx.katharsis.rest;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.jianglibo.wx.JsonApiListBodyWrapper;
import com.jianglibo.wx.KatharsisBase;
import com.jianglibo.wx.config.JsonApiResourceNames;
import com.jianglibo.wx.domain.BootUser;
import com.jianglibo.wx.katharsis.dto.UserDto;

public class TestUserFollowUnFollow  extends KatharsisBase {
	
	@Before
	public void b() throws JsonParseException, JsonMappingException, IOException {
		deleteAllUsers();
		jwtToken = getAdminJwtToken();
	}
	
	@Test
	public void t() throws Exception {
		BootUser befollowed = loginAsAdmin();
		BootUser follower = createBootUser("hello", "hello");
		
		JsonApiListBodyWrapper jbw = new JsonApiListBodyWrapper("users", befollowed.getId());
		
		response = addRelationWithContent(indentOm.writeValueAsString(jbw), "followeds", follower.getId(), jwtToken);
		assertResponseCode(response, 204);
		response = requestForBody(jwtToken, getItemUrl(befollowed.getId()) + "/followers");
		assertItemNumber(response, UserDto.class, 1);
		
		response = deleteRelationWithContent(indentOm.writeValueAsString(jbw), "followeds", follower.getId(), jwtToken);
		assertResponseCode(response, 204);
		response = requestForBody(jwtToken, getItemUrl(befollowed.getId()) + "/followers");
		assertItemNumber(response, UserDto.class, 0);
	}
	

	@Override
	protected String getResourceName() {
		return JsonApiResourceNames.BOOT_USER;
	}

}
