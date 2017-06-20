package com.jianglibo.wx.katharsis.rest;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.jianglibo.wx.KatharsisBase;
import com.jianglibo.wx.config.JsonApiResourceNames;
import com.jianglibo.wx.domain.BootGroup;
import com.jianglibo.wx.domain.BootUser;
import com.jianglibo.wx.katharsis.dto.GroupDto;

public class TestBootGrouptApi  extends KatharsisBase {
	
	
	private String jwtToken;
	
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
		
		au.getBootGroups().add(gp);
		bootUserRepo.save(au);
		
		bu.getBootGroups().add(gp);
		bootUserRepo.save(bu);

		
		cu.getBootGroups().add(gp);
		bootUserRepo.save(cu);
		
		response = requestForBody(jwtToken, getItemUrl(gp.getId()) + "/members");
		writeDto(response, JsonApiResourceNames.BOOT_GROUP, "getmembersrelations-self");
		List<GroupDto> posts = getList(response, GroupDto.class);
		assertThat(posts.size(), equalTo(1));
		
		response = requestForBody(jwtToken, getItemUrl(gp.getId()) + "/members?page[limit]=1&page[offset]=0");
		writeDto(response, JsonApiResourceNames.BOOT_GROUP, "getmemberssrelations-self");
		posts = getList(response, GroupDto.class);
		assertThat(posts.size(), equalTo(1));
	}


	@Override
	protected String getResourceName() {
		return JsonApiResourceNames.BOOT_GROUP;
	}

}
