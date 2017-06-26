package com.jianglibo.wx.katharsis.rest;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.jianglibo.wx.JsonApiListBodyWrapper;
import com.jianglibo.wx.KatharsisBase;
import com.jianglibo.wx.config.JsonApiResourceNames;
import com.jianglibo.wx.domain.BootUser;

public class TestUserRoleRelation  extends KatharsisBase {
	
	@Before
	public void b() throws JsonParseException, JsonMappingException, IOException {
		deleteAllUsers();
		jwtToken = getAdminJwtToken();
	}
	
	@Test
	public void tAddOne() throws JsonParseException, JsonMappingException, IOException {
		createBootUser("b1", "123", "a", "b", "c");
		BootUser bu1 = createBootUser("b2", "123");
		
		
		
		JsonApiListBodyWrapper jl = new JsonApiListBodyWrapper(JsonApiResourceNames.ROLE, roleRepo.findByName("ROLE_A").getId(), roleRepo.findByName("ROLE_B").getId());
		String s = indentOm.writeValueAsString(jl);
		response = addRelationWithContent(s, "roles", bu1.getId(), jwtToken);
		assertResponseCode(response, 204);
		
		bu1 = bootUserRepo.findOne(bu1.getId());
		assertThat(bu1.getRoles().size(), equalTo(3));
		
		response = deleteRelationWithContent(s, "roles", bu1.getId(), jwtToken);
		assertResponseCode(response, 204);
		
		bu1 = bootUserRepo.findOne(bu1.getId());
		assertThat(bu1.getRoles().size(), equalTo(1));
	}


	@Override
	protected String getResourceName() {
		return JsonApiResourceNames.BOOT_USER;
	}

}
