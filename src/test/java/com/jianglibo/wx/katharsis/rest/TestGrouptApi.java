package com.jianglibo.wx.katharsis.rest;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.jianglibo.wx.JsonApiPostBodyWrapper;
import com.jianglibo.wx.JsonApiPostBodyWrapperBuilder;
import com.jianglibo.wx.KatharsisBase;
import com.jianglibo.wx.config.JsonApiResourceNames;
import com.jianglibo.wx.katharsis.dto.GroupDto;

public class TestGrouptApi  extends KatharsisBase {
	
	@Before
	public void b() throws JsonParseException, JsonMappingException, IOException {
		initTestUser();
		delteAllGroups();
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
		
		response = postItemWithContent(bodys, jwt1);
		
		writeDto(response, getResourceName(), ActionNames.POST_RESULT);
		
		GroupDto newGroup = getOne(response.getBody(), GroupDto.class);
		assertThat(newGroup.getName(), equalTo("agroup"));
		assertThat(newGroup.getDescription(), equalTo("agroupdescription"));
		assertThat(newGroup.getThumbUrl(), equalTo("abc"));
		assertTrue(newGroup.isOpenToAll());
		
		response = requestForBody(jwt1, getBaseURI());
		writeDto(response, getResourceName(), ActionNames.GET_LIST);
		
		response = requestForBody(jwt1, getItemUrl(newGroup.getId()));
		
		writeDto(response, getResourceName(), ActionNames.GET_ONE);
		deleteByExchange(jwt1, getItemUrl(newGroup.getId()));
	}

	@Override
	protected String getResourceName() {
		return JsonApiResourceNames.BOOT_GROUP;
	}

}
