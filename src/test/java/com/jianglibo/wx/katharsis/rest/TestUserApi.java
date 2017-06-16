package com.jianglibo.wx.katharsis.rest;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.jianglibo.wx.KatharsisBase;
import com.jianglibo.wx.config.JsonApiResourceNames;
import com.jianglibo.wx.domain.BootUser;
import com.jianglibo.wx.katharsis.dto.UserDto;
import com.jianglibo.wx.repository.BootUserRepository;

public class TestUserApi  extends KatharsisBase {
	
	private static final String USER_T1 = "USER_T1";
	
	private String jwtToken;
	
	@Autowired
	private BootUserRepository userRepository;
	
	@Before
	public void b() throws JsonParseException, JsonMappingException, IOException {
		deleteAllUsers();
		jwtToken = getAdminJwtToken();
	}
	
	@Test
	public void tAddOne() throws JsonParseException, JsonMappingException, IOException {
		long c= userRepository.count();
		assertThat(c, equalTo(1L));
		ResponseEntity<String> response = postItem(jwtToken);
		writeDto(response, getResourceName(), ActionNames.POST_RESULT);
		assertThat(response.getStatusCodeValue(), equalTo(HttpStatus.CREATED.value()));
		UserDto newUser = getOne(response.getBody(), UserDto.class);
		assertThat(newUser.getName(), equalTo(USER_T1));
		assertTrue("password should be empty.", newUser.getPassword() == null || newUser.getPassword().isEmpty());
		assertThat("id should great than 0.", newUser.getId(), greaterThan(0L));
		response = requestForBody(jwtToken, getItemUrl(newUser.getId()));
		writeDto(response, getResourceName(), ActionNames.GET_ONE);
		response = deleteByExchange(jwtToken, getItemUrl(newUser.getId()));
		assertThat(userRepository.count(), equalTo(c));
	}
	
	@Test
	public void tGet() throws JsonParseException, JsonMappingException, IOException {
		ResponseEntity<String> response = requestForBody(jwtToken, getBaseURI());
		writeDto(response, getResourceName(), ActionNames.GET_LIST);
	}
	
	@Test
	public void tDeleteSelf() throws JsonParseException, JsonMappingException, IOException {
		BootUser bu = userRepository.findByName("admin");
		ResponseEntity<String> response = deleteByExchange(jwtToken, getItemUrl(bu.getId()));
		getErrors(response);
	}

	@Override
	protected String getResourceName() {
		return JsonApiResourceNames.BOOT_USER;
	}

}
