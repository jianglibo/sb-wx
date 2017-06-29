package com.jianglibo.wx.katharsis.rest;

import java.io.IOException;
import java.util.stream.IntStream;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.jianglibo.wx.JsonApiListBodyWrapper;
import com.jianglibo.wx.KatharsisBase;
import com.jianglibo.wx.config.JsonApiResourceNames;
import com.jianglibo.wx.domain.BootUser;
import com.jianglibo.wx.domain.FollowRelation;
import com.jianglibo.wx.katharsis.dto.UserDto;
import com.jianglibo.wx.repository.FollowRelationRepository;

public class TestUserFollowUnFollow  extends KatharsisBase {
	
	@Autowired
	private FollowRelationRepository frRepo;
	
	@Before
	public void b() throws JsonParseException, JsonMappingException, IOException {
		deleteAllUsers();
		jwtToken = getAdminJwtToken();
	}
	
	@Test
	public void t() throws Exception {
		BootUser befollowed = loginAsAdmin();
		BootUser follower = createBootUser("hello", "hello");
		
		// you can only initiative follow a user. cannot force a user to follow you.
		JsonApiListBodyWrapper jbw = new JsonApiListBodyWrapper("users", befollowed.getId());
		
		response = addRelationWithContent(indentOm.writeValueAsString(jbw), "followeds", follower.getId(), jwtToken);
		assertResponseCode(response, 204);
		response = requestForBody(jwtToken, getItemUrl(befollowed.getId()) + "/followers");
		writeDto(response.getBody(), getResourceName(), "followers");
		assertItemNumber(response, UserDto.class, 1);
		
		response = requestForBody(jwtToken, getItemUrl(follower.getId()) + "/followeds");
		writeDto(response.getBody(), getResourceName(), "followeds");
		assertItemNumber(response, UserDto.class, 1);

		
		response = deleteRelationWithContent(indentOm.writeValueAsString(jbw), "followeds", follower.getId(), jwtToken);
		assertResponseCode(response, 204);
		response = requestForBody(jwtToken, getItemUrl(befollowed.getId()) + "/followers");
		assertItemNumber(response, UserDto.class, 0);
		
		
		IntStream.range(0, 25).mapToObj(i -> createBootUser("hello" + i, "hello")).map(u -> new FollowRelation(u, befollowed)).forEach(fr -> frRepo.save(fr));
		response = requestForBody(jwtToken, getItemUrl(befollowed.getId()) + "/followers");
		assertMetaNumber(response, 25);

	}

	@Override
	protected String getResourceName() {
		return JsonApiResourceNames.BOOT_USER;
	}

}
