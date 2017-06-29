package com.jianglibo.wx.katharsis.rest;

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
import com.jianglibo.wx.repository.FollowRelationRepository;

public class TestUserApi  extends KatharsisBase {
	
	@Autowired
	private FollowRelationRepository frRepo;
	
	@Autowired
	private ApplicationContext applicationContext;
	
	private AuthenticationManager getAuthenticationManager() {
		return applicationContext.getBean(AuthenticationManager.class);
	}
	
	@Before
	public void b() throws JsonParseException, JsonMappingException, IOException {
		deleteAllUsers();
		jwtToken = getAdminJwtToken();
	}
	
	@Test
	public void tAddOne() throws JsonParseException, JsonMappingException, IOException {
		long c= bootUserRepo.count();
		assertThat(c, equalTo(1L));
		
		BootUser bu = createBootUser("b1", "123", "a", "b", "c");
		
		Long[] roleids = bu.getRoles().stream().map(r -> r.getId()).toArray(size -> new Long[size]);
		
		JsonApiPostBodyWrapper<CreateListBody> jbw = JsonApiPostBodyWrapperBuilder.getListRelationBuilder(getResourceName())
				.addAttributePair("name", "name1334")
				.addAttributePair("email", "ab@c.com")
				.addAttributePair("mobile", "13777777777")
				.addAttributePair("password", "akka123456")
				.addRelation("roles", JsonApiResourceNames.ROLE, roleids).build();
		
		String s = indentOm.writeValueAsString(jbw);
		writeDto(s, JsonApiResourceNames.BOOT_USER, "postcontent");
		
		ResponseEntity<String> response = postItemWithContent(s, jwtToken);
		
		writeDto(response, getResourceName(), ActionNames.POST_RESULT);
		assertThat(response.getStatusCodeValue(), equalTo(HttpStatus.CREATED.value()));
		UserDto newUser = getOne(response.getBody(), UserDto.class);
		
		BootUser u = bootUserRepo.findOne(newUser.getId());
		assertThat(u.getRoles().size(), equalTo(4));
		
		assertThat(newUser.getName(), equalTo("name1334"));
		assertTrue("password should be empty.", newUser.getPassword() == null || newUser.getPassword().isEmpty());
		assertThat("id should great than 0.", newUser.getId(), greaterThan(0L));
		response = requestForBody(jwtToken, getItemUrl(newUser.getId()));
		writeDto(response, getResourceName(), ActionNames.GET_ONE);
		
		response = deleteByExchange(jwtToken, getItemUrl(newUser.getId()));
		assertThat(bootUserRepo.count(), equalTo(c + 1));
	}
	
	@Test
	public void tChangePasswd() throws JsonParseException, JsonMappingException, IOException {
		long c= bootUserRepo.count();
		assertThat(c, equalTo(1L));
		
		BootUser bu = createBootUser("b111111111", "1231111111", "a", "b", "c");
		
		Long[] roleids = bu.getRoles().stream().map(r -> r.getId()).toArray(size -> new Long[size]);
		
		JsonApiPostBodyWrapper<CreateListBody> jbw = JsonApiPostBodyWrapperBuilder.getListRelationBuilder(getResourceName())
				.addAttributePair("name", "name1334")
				.addAttributePair("email", "ab@c.com")
				.addAttributePair("mobile", "13777777777")
				.addAttributePair("password", "akka123456")
				.addAttributePair("dtoAction", "modify")
				.addAttributePair("dtoApplyTo", "password")
				.addRelation("roles", JsonApiResourceNames.ROLE, roleids).build();
		
		String s = indentOm.writeValueAsString(jbw);
		response = patchByExchange(s, jwtToken, getItemUrl(bu.getId()));
		writeDto(response, getResourceName(), ActionNames.PATCH_RESULT);
		
		UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken("b111111111",	"akka123456");
		getAuthenticationManager().authenticate(authRequest);
	}
	
	@Test
	public void tGet() throws JsonParseException, JsonMappingException, IOException {
		ResponseEntity<String> response = requestForBody(jwtToken, getBaseURI());
		writeDto(response, getResourceName(), ActionNames.GET_LIST);
	}
	
	@Test
	public void tDeleteSelf() throws JsonParseException, JsonMappingException, IOException {
		BootUser bu = bootUserRepo.findByName("admin");
		ResponseEntity<String> response = deleteByExchange(jwtToken, getItemUrl(bu.getId()));
		getErrors(response);
	}

	@Override
	protected String getResourceName() {
		return JsonApiResourceNames.BOOT_USER;
	}

}
