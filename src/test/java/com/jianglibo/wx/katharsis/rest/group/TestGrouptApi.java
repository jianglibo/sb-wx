package com.jianglibo.wx.katharsis.rest.group;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.jianglibo.wx.JsonApiPostBodyWrapper;
import com.jianglibo.wx.JsonApiPostBodyWrapperBuilder;
import com.jianglibo.wx.KatharsisBase;
import com.jianglibo.wx.config.JsonApiResourceNames;
import com.jianglibo.wx.domain.GroupUserRelation;
import com.jianglibo.wx.katharsis.dto.GroupDto;
import com.jianglibo.wx.repository.GroupUserRelationRepository;

public class TestGrouptApi  extends KatharsisBase {
	
	@Autowired
	private GroupUserRelationRepository gurRepo;
	
	@Before
	public void b() throws JsonParseException, JsonMappingException, IOException {
		initTestUser();
	}
	
	@Test
	public void tAdmionAddOne() throws JsonParseException, JsonMappingException, IOException {
		
		JsonApiPostBodyWrapper<?> body = JsonApiPostBodyWrapperBuilder.getObjectRelationBuilder(getResourceName())
				.addAttributePair("name", "agroup")
				.addAttributePair("description", "agroupdescription")
				.addAttributePair("openToAll", true)
				.addAttributePair("thumbUrl", "abc")
				.build();
		
		String bodys = indentOm.writeValueAsString(body);
		writeDto(bodys, getResourceName(), "postcontent");
		
		// every one is capable to create any number of groups.
		response = postItemWithContent(bodys, jwt1);
		
		writeDto(response, getResourceName(), ActionNames.POST_RESULT);
		
		GroupDto newGroup = getOne(response.getBody(), GroupDto.class);
		assertThat(newGroup.getName(), equalTo("agroup"));
		assertThat(newGroup.getDescription(), equalTo("agroupdescription"));
		assertThat(newGroup.getThumbUrl(), equalTo("abc"));
		assertTrue(newGroup.isOpenToAll());
		
		// only the administrator can get group list. 
		response = requestForBody(jwt1, getBaseURI());
		writeDto(response, getResourceName(), ActionNames.GET_LIST);
		assertThat(response.getStatusCodeValue(), equalTo(200));
		
		response = requestForBody(jwt1, getItemUrl(newGroup.getId()));
		
		writeDto(response, getResourceName(), ActionNames.GET_ONE);
		
		// access denied.
		response = deleteByExchange(jwt2, getItemUrl(newGroup.getId()));
		assertAccessDenied(response);
		
		// add user to group.
		GroupUserRelation gur = new GroupUserRelation(groupRepo.findOne(newGroup.getId()), user2);
		gurRepo.save(gur);
		
		deleteByExchange(jwt1, getItemUrl(newGroup.getId()));
	}

	@Override
	protected String getResourceName() {
		return JsonApiResourceNames.BOOT_GROUP;
	}

}
