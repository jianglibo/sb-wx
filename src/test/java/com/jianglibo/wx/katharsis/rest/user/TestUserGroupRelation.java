package com.jianglibo.wx.katharsis.rest.user;

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
import com.jianglibo.wx.katharsis.dto.ApproveDto;
import com.jianglibo.wx.katharsis.dto.GroupDto;
import com.jianglibo.wx.katharsis.dto.UserDto;

public class TestUserGroupRelation  extends KatharsisBase {
	
	@Before
	public void b() throws JsonParseException, JsonMappingException, IOException {
		groupRepo.deleteAll();
		initTestUser();
	}
	
	@Test
	public void tOpenGroup() throws JsonParseException, JsonMappingException, IOException {
		BootGroup bg = new BootGroup("agroup");
		bg.setOpenToAll(true);
		bg.setCreator(user1);
		bg = groupRepo.save(bg);
		
		assertTrue(bg.getMembers().isEmpty());
		
		JsonApiListBodyWrapper jl = new JsonApiListBodyWrapper(JsonApiResourceNames.BOOT_GROUP, bg.getId());
		
		String s = indentOm.writeValueAsString(jl);
		
		response = addRelationWithContent(s, "joinedGroups", user1.getId(), jwt1);
		assertResponseCode(response, 204);
		
		// because group is opening to all, no approve created.
		response = requestForBody(jwt1, getBaseURI() + "/" + user1.getId()  + "/sentApproves");
		assertItemNumber(response, ApproveDto.class, 0);

		
		// user's joined group should be 1.
		response = requestForBody(jwt1, getBaseURI() + "/" + user1.getId()  + "/joinedGroups");
		assertItemNumber(response, GroupDto.class, 1);
		
		// group's members should be 1.
		response = requestForBody(jwt1, getBaseURI(JsonApiResourceNames.BOOT_GROUP) + "/" + bg.getId()  + "/members");
		assertItemNumber(response, UserDto.class, 1);
		
		
		response = deleteRelationWithContent(s, "joinedGroups", user1.getId(), jwt1);
		assertResponseCode(response, 204);
		
		// user's joined group should be 0.
		response = requestForBody(jwt1, getBaseURI() + "/" + user1.getId()  + "/joinedGroups");
		assertItemNumber(response, GroupDto.class, 0);
		
		// group's members should be 0.
		response = requestForBody(jwt1, getBaseURI(JsonApiResourceNames.BOOT_GROUP) + "/" + bg.getId()  + "/members");
		assertItemNumber(response, UserDto.class, 0);
		
		// only group member can browser group members.
		response = requestForBody(jwt2, getBaseURI(JsonApiResourceNames.BOOT_GROUP) + "/" + bg.getId()  + "/members");
		assertAccessDenied(response);
		
		// user2 join the group.
		response = addRelationWithContent(s, "joinedGroups", user2.getId(), jwt2);
		assertResponseCode(response, 204);

		// now user2 can access group members.
		response = requestForBody(jwt2, getBaseURI(JsonApiResourceNames.BOOT_GROUP) + "/" + bg.getId()  + "/members");
		assertItemNumber(response, UserDto.class, 1);
	}
	
	@Test
	public void tCloseGroup() throws JsonParseException, JsonMappingException, IOException {
		BootGroup bg = new BootGroup("agroup");
		bg.setOpenToAll(false);
		bg.setCreator(user1);
		bg = groupRepo.save(bg);
		
		assertTrue(bg.getMembers().isEmpty());
		
		JsonApiListBodyWrapper jl = new JsonApiListBodyWrapper(JsonApiResourceNames.BOOT_GROUP, bg.getId());
		
		String s = indentOm.writeValueAsString(jl);
		
		response = addRelationWithContent(s, "joinedGroups", user2.getId(), jwt2);
		assertResponseCode(response, 204);
		
		// it's a private group, so a request approve is created, and sent to user1.
		response = requestForBody(jwt2, getBaseURI() + "/" + user2.getId()  + "/sentApproves");
		writeDto(response.getBody(), getResourceName(), "sentApproves");
		assertItemNumber(response, ApproveDto.class, 1);
		
		// member number is not changed.
		response = requestForBody(jwt1, getBaseURI(JsonApiResourceNames.BOOT_GROUP) + "/" + bg.getId()  + "/members");
		assertItemNumber(response, UserDto.class, 0);

	}


	@Override
	protected String getResourceName() {
		return JsonApiResourceNames.BOOT_USER;
	}
}
