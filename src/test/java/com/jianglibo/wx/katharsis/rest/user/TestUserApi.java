package com.jianglibo.wx.katharsis.rest.user;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.jianglibo.wx.JsonApiPostBodyWrapper;
import com.jianglibo.wx.JsonApiPostBodyWrapper.CreateListBody;
import com.jianglibo.wx.JsonApiPostBodyWrapperBuilder;
import com.jianglibo.wx.KatharsisBase;
import com.jianglibo.wx.config.JsonApiResourceNames;
import com.jianglibo.wx.domain.BootUser;
import com.jianglibo.wx.katharsis.dto.UserDto;

public class TestUserApi  extends KatharsisBase {
	
	@Autowired
	private ApplicationContext applicationContext;
	
	private AuthenticationManager getAuthenticationManager() {
		return applicationContext.getBean(AuthenticationManager.class);
	}
	
	@Before
	public void b() throws JsonParseException, JsonMappingException, IOException {
		initTestUser();
	}
	
	@Test
	public void tAddOne() throws JsonParseException, JsonMappingException, IOException {
		BootUser bu = tutil.createBootUser("b1", "123", "a", "b", "c");
		Long[] roleids = bu.getRoles().stream().map(r -> r.getId()).toArray(size -> new Long[size]);
		
		JsonApiPostBodyWrapper<CreateListBody> jbw = JsonApiPostBodyWrapperBuilder.getListRelationBuilder(getResourceName())
				.addAttributePair("name", "name1334")
				.addAttributePair("email", "ab@c.com")
				.addAttributePair("mobile", "13777777777")
				.addAttributePair("password", "akka123456")
				.addRelation("roles", JsonApiResourceNames.ROLE, roleids).build();
		
		String s = indentOm.writeValueAsString(jbw);
		writeDto(s, JsonApiResourceNames.BOOT_USER, "postcontent");
		
		// every can add a new user.
		ResponseEntity<String> response = postItemWithContent(s, null);
		
		writeDto(response, getResourceName(), ActionNames.POST_RESULT);
		assertThat(response.getStatusCodeValue(), equalTo(HttpStatus.CREATED.value()));
		
		UserDto newUser = getOne(response.getBody(), UserDto.class);
		
		BootUser u = bootUserRepo.findOne(newUser.getId());
		assertThat(u.getRoles().size(), equalTo(4));
		
		assertThat(newUser.getName(), equalTo("name1334"));
		assertTrue("password should be empty.", newUser.getPassword() == null || newUser.getPassword().isEmpty());
		assertThat("id should great than 0.", newUser.getId(), greaterThan(0L));
		response = requestForBody(jwt1, getItemUrl(newUser.getId()));
		writeDto(response, getResourceName(), ActionNames.GET_ONE);
		
		response = deleteByExchange(jwt1, getItemUrl(newUser.getId()));
	}
	
	@Test
	public void tChangePasswd() throws JsonParseException, JsonMappingException, IOException {
		String uname = "b111111111";
		String upass = "1231111111";
		String nupass = "akka123456";
		
		BootUser bu = tutil.createBootUser(uname, upass, "a", "b", "c");
		
		Long[] roleids = bu.getRoles().stream().map(r -> r.getId()).toArray(size -> new Long[size]);
		
		JsonApiPostBodyWrapper<CreateListBody> jbw = JsonApiPostBodyWrapperBuilder.getListRelationBuilder(getResourceName())
				.addAttributePair("name", "name1334")
				.addAttributePair("email", "ab@c.com")
				.addAttributePair("mobile", "13777777777")
				.addAttributePair("password", nupass)
				.addAttributePair("dtoAction", "modify")
				.addAttributePair("dtoApplyTo", "password")
				.addRelation("roles", JsonApiResourceNames.ROLE, roleids).build();
		
		String s = indentOm.writeValueAsString(jbw);
		// user1 don't allow to change bu's password.
		response = patchByExchange(s, jwt1, getItemUrl(bu.getId()));
		assertAccessDenied(response);
		
		String jwt = getJwtToken(uname, upass, "a","b","c");
		response = patchByExchange(s, jwt, getItemUrl(bu.getId()));
		writeDto(response, getResourceName(), ActionNames.PATCH_RESULT);
		UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken("b111111111",	nupass);
		getAuthenticationManager().authenticate(authRequest);
	}
	
	@Test
	public void tGet() throws JsonParseException, JsonMappingException, IOException {
		ResponseEntity<String> response = requestForBody(jwt1, getBaseURI());
		writeDto(response, getResourceName(), ActionNames.GET_LIST);
	}
	
	@Test
	public void tDeleteSelf() throws JsonParseException, JsonMappingException, IOException {
		ResponseEntity<String> response = deleteByExchange(jwt1, getItemUrl(user1.getId()));
		getErrors(response);
	}

	@Override
	protected String getResourceName() {
		return JsonApiResourceNames.BOOT_USER;
	}

}
