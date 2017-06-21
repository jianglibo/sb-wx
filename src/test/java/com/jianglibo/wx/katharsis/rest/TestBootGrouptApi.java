package com.jianglibo.wx.katharsis.rest;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.jianglibo.wx.KatharsisBase;
import com.jianglibo.wx.config.JsonApiResourceNames;
import com.jianglibo.wx.domain.BootGroup;
import com.jianglibo.wx.domain.BootUser;
import com.jianglibo.wx.domain.GroupUserRelation;
import com.jianglibo.wx.katharsis.dto.GroupDto;
import com.jianglibo.wx.katharsis.dto.UserDto;
import com.jianglibo.wx.repository.GroupUserRelationRepository;

public class TestBootGrouptApi  extends KatharsisBase {
	
	
	private String jwtToken;
	
	@Autowired
	private GroupUserRelationRepository gurRepo;
	
	@Before
	public void b() throws JsonParseException, JsonMappingException, IOException {
		delteAllGroups();
		jwtToken = getAdminJwtToken();
	}
	
	@Test
	public void tAdmionAddOne() throws JsonParseException, JsonMappingException, IOException {
		ResponseEntity<String> response = postItem(jwtToken);
		writeDto(response, getResourceName(), ActionNames.POST_RESULT);
		
		GroupDto newPost = getOne(response.getBody(), GroupDto.class);
		assertThat(newPost.getName(), equalTo("agroup"));
		
		response = requestForBody(jwtToken, getBaseURI());
		writeDto(response, getResourceName(), ActionNames.GET_LIST);
		response = requestForBody(jwtToken, getItemUrl(newPost.getId()));
		writeDto(response, getResourceName(), ActionNames.GET_ONE);
		deleteByExchange(jwtToken, getItemUrl(newPost.getId()));
	}
	
	@Test
	public void getMemberssRelationsSelf() throws Exception {
		BootGroup gp = new BootGroup("agroup");
		gp  = groupRepo.save(gp);
		
		BootUser au = createBootUser("aaa", "1234");
		BootUser bu = createBootUser("bbb", "1234");
		BootUser cu = createBootUser("ccc", "1234");
		
		GroupUserRelation gura = new GroupUserRelation(gp, au);
		gurRepo.save(gura);
		gura = new GroupUserRelation(gp, bu);
		gurRepo.save(gura);
		gura = new GroupUserRelation(gp, cu);
		gurRepo.save(gura);
		
		response = requestForBody(jwtToken, getItemUrl(gp.getId()) + "/members");
		writeDto(response, JsonApiResourceNames.BOOT_GROUP, "getmembersrelations-self");
		List<UserDto> users = getList(response, UserDto.class);
		assertThat(users.size(), equalTo(3));
		
		BootUser du = createBootUser("ddd", "1234");
		JsonApiBodyWrapper jbw = new JsonApiBodyWrapper("users", du.getId());
		// add relation.
		response = addRelationWithContent(indentOm.writeValueAsString(jbw), "members", gp.getId(), jwtToken);
		assertThat(response.getStatusCodeValue(), equalTo(204));
		response = requestForBody(jwtToken, getItemUrl(gp.getId()) + "/members");
		writeDto(response, JsonApiResourceNames.BOOT_GROUP, "getmembersrelations-self");
		users = getList(response, UserDto.class);
		
		assertThat(response.getStatusCodeValue(), equalTo(200));
		assertThat(users.size(), equalTo(4));
		assertThat(gurRepo.count(), equalTo(4L));
		// delete a relation
		response = deleteRelationWithContent(indentOm.writeValueAsString(jbw), "members", gp.getId(), jwtToken);
		
		response = requestForBody(jwtToken, getItemUrl(gp.getId()) + "/members");
		writeDto(response, JsonApiResourceNames.BOOT_GROUP, "getmembersrelations-self");
		users = getList(response, UserDto.class);
		assertThat(gurRepo.count(), equalTo(3L));
		assertThat(users.size(), equalTo(3));
		
		response = requestForBody(jwtToken, getItemUrl(gp.getId()) + "/members?page[limit]=1&page[offset]=0");
		writeDto(response, JsonApiResourceNames.BOOT_GROUP, "getmemberssrelations-self");
		users = getList(response, UserDto.class);
		assertThat(users.size(), equalTo(1));
		
		
		
		printme(indentOm.writeValueAsString(jbw));
	}


	@Override
	protected String getResourceName() {
		return JsonApiResourceNames.BOOT_GROUP;
	}

}
