package com.jianglibo.wx.katharsis.rest.user;

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
		initTestUser();
	}
	
	@Test
	public void t() throws Exception {
		BootUser befollowed = user1;
		BootUser follower = user2;
		
		// you can only initiative follow a user. cannot force a user to follow you.
		JsonApiListBodyWrapper jbw = new JsonApiListBodyWrapper("users", befollowed.getId());
		String body = indentOm.writeValueAsString(jbw);
		
		// user2 want to follow user1. From follower's point of view, It's just adding a relation to my followeds.
		// The body content is the Id of user about to follow!!!! The url is /users/myid/relationships/followeds
		// as a rule, change myself instead of others.
		response = addRelationWithContent(body, "followeds", follower.getId(), jwt2);
		assertResponseCode(response, 204);
		
		// get my(user1) followers.
		response = requestForBody(jwt1, getItemUrl(befollowed.getId()) + "/followers");
		writeDto(response.getBody(), getResourceName(), "followers");
		assertItemNumber(response, UserDto.class, 1);
		
		// get my(user1) followeds.
		response = requestForBody(jwt1, getItemUrl(befollowed.getId()) + "/followeds");
		writeDto(response.getBody(), getResourceName(), "followeds");
		assertItemNumber(response, UserDto.class, 0);

		// Who can delete relation? The follower.
		response = deleteRelationWithContent(body, "followeds", follower.getId(), jwt1);
		assertAccessDenied(response);
		response = deleteRelationWithContent(body, "followeds", follower.getId(), jwt2);
		assertResponseCode(response, 204);
		
		
		response = requestForBody(jwt1, getItemUrl(befollowed.getId()) + "/followers");
		assertItemNumber(response, UserDto.class, 0);
		
		
		IntStream.range(0, 25).mapToObj(i -> tutil.createBootUser("hello" + i, "hello")).map(u -> new FollowRelation(u, befollowed)).forEach(fr -> frRepo.save(fr));
		response = requestForBody(jwt1, getItemUrl(befollowed.getId()) + "/followers");
		assertMetaNumber(response, 25);

	}

	@Override
	protected String getResourceName() {
		return JsonApiResourceNames.BOOT_USER;
	}

}
