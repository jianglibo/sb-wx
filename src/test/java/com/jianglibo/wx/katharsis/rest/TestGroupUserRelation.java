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
import com.jianglibo.wx.JsonApiListBodyWrapper;
import com.jianglibo.wx.KatharsisBase;
import com.jianglibo.wx.config.JsonApiResourceNames;
import com.jianglibo.wx.domain.BootGroup;
import com.jianglibo.wx.domain.BootUser;
import com.jianglibo.wx.domain.GroupUserRelation;
import com.jianglibo.wx.katharsis.dto.UserDto;
import com.jianglibo.wx.repository.GroupUserRelationRepository;

public class TestGroupUserRelation  extends KatharsisBase {
	
	
	@Autowired
	private GroupUserRelationRepository gurRepo;
	
	@Before
	public void b() throws JsonParseException, JsonMappingException, IOException {
		delteAllGroups();
		deleteAllUsers();
		initTestUser();
	}
	
	@Test
	public void getMembersRelation() throws Exception {
		BootUser cu = tutil.createBootUser("ccc", "1234");

		BootGroup gp = new BootGroup("agroup");
		gp.setCreator(user1);
		gp  = groupRepo.save(gp);
		
		
		GroupUserRelation gura = new GroupUserRelation(gp, user1);
		gurRepo.save(gura);
		gura = new GroupUserRelation(gp, user2);
		gurRepo.save(gura);
		gura = new GroupUserRelation(gp, cu);
		gurRepo.save(gura);
		
		response = requestForBody(jwt1, getItemUrl(gp.getId()) + "/members");
		writeDto(response, JsonApiResourceNames.BOOT_GROUP, "membersrelation");
		List<UserDto> users = getList(response, UserDto.class);
		assertThat(users.size(), equalTo(3));
		
		String jwtToken1 = getJwtToken("bbb", "1234");
		response = requestForBody(jwtToken1, getItemUrl(gp.getId()) + "/members");
		assertAccessDenied(response);
		
		BootUser du = tutil.createBootUser("ddd", "1234");
		JsonApiListBodyWrapper jbw = new JsonApiListBodyWrapper("users", du.getId());
		
		String  body = indentOm.writeValueAsString(jbw);
		writeDto(body, getResourceName(), "altermember");
		
		// add relation.
		response = addRelationWithContent(body, "members", gp.getId(), jwt1);
		assertThat(response.getStatusCodeValue(), equalTo(204));
		response = requestForBody(jwt1, getItemUrl(gp.getId()) + "/members");
		
		users = getList(response, UserDto.class);
		assertThat(response.getStatusCodeValue(), equalTo(200));
		assertThat(users.size(), equalTo(4));
		assertThat(gurRepo.count(), equalTo(4L));
		
		// delete a relation
		response = deleteRelationWithContent(body, "members", gp.getId(), jwt1);
		response = requestForBody(jwt1, getItemUrl(gp.getId()) + "/members");
		users = getList(response, UserDto.class);
		assertThat(gurRepo.count(), equalTo(3L));
		assertThat(users.size(), equalTo(3));
		
		// pagination
		response = requestForBody(jwt1, getItemUrl(gp.getId()) + "/members?page[limit]=1&page[offset]=0");
		users = getList(response, UserDto.class);
		assertThat(users.size(), equalTo(1));
	}


	@Override
	protected String getResourceName() {
		return JsonApiResourceNames.BOOT_GROUP;
	}

}
