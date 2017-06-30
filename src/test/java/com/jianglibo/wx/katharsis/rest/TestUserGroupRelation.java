package com.jianglibo.wx.katharsis.rest;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.jianglibo.wx.JsonApiListBodyWrapper;
import com.jianglibo.wx.KatharsisBase;
import com.jianglibo.wx.config.JsonApiResourceNames;
import com.jianglibo.wx.domain.BootGroup;
import com.jianglibo.wx.domain.BootUser;
import com.jianglibo.wx.katharsis.dto.ApproveDto;
import com.jianglibo.wx.katharsis.dto.GroupDto;
import com.jianglibo.wx.katharsis.dto.UserDto;

public class TestUserGroupRelation  extends KatharsisBase {
	
	@Before
	public void b() throws JsonParseException, JsonMappingException, IOException {
		deleteAllUsers();
		groupRepo.deleteAll();
		jwtToken = getAdminJwtToken();
	}
	
	@Test
	public void tOpenGroup() throws JsonParseException, JsonMappingException, IOException {
		BootUser bu1 = tutil.createBootUser("b2", "123");
		
		BootGroup bg = new BootGroup("agroup");
		bg.setOpenToAll(true);
		bg = groupRepo.save(bg);
		
		assertTrue(bg.getMembers().isEmpty());
		
		JsonApiListBodyWrapper jl = new JsonApiListBodyWrapper(JsonApiResourceNames.BOOT_GROUP, bg.getId());
		
		String s = indentOm.writeValueAsString(jl);
		
		response = addRelationWithContent(s, "joinedGroups", bu1.getId(), jwtToken);
		assertResponseCode(response, 204);
		
		// because group is opening to all, no approve created.
		response = requestForBody(jwtToken, getBaseURI() + "/" + bu1.getId()  + "/sentApproves");
		assertItemNumber(response, ApproveDto.class, 0);

		
		// user's joined group should be 1.
		response = requestForBody(jwtToken, getBaseURI() + "/" + bu1.getId()  + "/joinedGroups");
		assertItemNumber(response, GroupDto.class, 1);
		
		// group's members should be 1.
		response = requestForBody(jwtToken, getBaseURI(JsonApiResourceNames.BOOT_GROUP) + "/" + bg.getId()  + "/members");
		assertItemNumber(response, UserDto.class, 1);
		
		
		response = deleteRelationWithContent(s, "joinedGroups", bu1.getId(), jwtToken);
		assertResponseCode(response, 204);
		
		// user's joined group should be 0.
		response = requestForBody(jwtToken, getBaseURI() + "/" + bu1.getId()  + "/joinedGroups");
		assertItemNumber(response, GroupDto.class, 0);
		
		// group's members should be 0.
		response = requestForBody(jwtToken, getBaseURI(JsonApiResourceNames.BOOT_GROUP) + "/" + bg.getId()  + "/members");
		assertItemNumber(response, UserDto.class, 0);
	}
	
	@Test
	public void tCloseGroup() throws JsonParseException, JsonMappingException, IOException {
		BootUser bu1 = tutil.createBootUser("b2", "123");
		
		BootGroup bg = new BootGroup("agroup");
		bg.setOpenToAll(false);
		bg = groupRepo.save(bg);
		
		assertTrue(bg.getMembers().isEmpty());
		
		JsonApiListBodyWrapper jl = new JsonApiListBodyWrapper(JsonApiResourceNames.BOOT_GROUP, bg.getId());
		
		String s = indentOm.writeValueAsString(jl);
		
		response = addRelationWithContent(s, "joinedGroups", bu1.getId(), jwtToken);
		assertResponseCode(response, 204);
		
		// because group isn't opening to all, no approve created.
		response = requestForBody(jwtToken, getBaseURI() + "/" + bu1.getId()  + "/sentApproves");
		writeDto(response.getBody(), getResourceName(), "sentApproves");
		assertItemNumber(response, ApproveDto.class, 1);
	}


	@Override
	protected String getResourceName() {
		return JsonApiResourceNames.BOOT_USER;
	}

}
