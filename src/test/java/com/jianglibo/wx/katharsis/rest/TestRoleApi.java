package com.jianglibo.wx.katharsis.rest;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.jianglibo.wx.KatharsisBase;
import com.jianglibo.wx.config.JsonApiResourceNames;
import com.jianglibo.wx.jwt.JwtUtil;
import com.jianglibo.wx.katharsis.dto.RoleDto;
import com.jianglibo.wx.katharsis.exception.AppExceptionMapper;

import io.katharsis.errorhandling.ErrorData;
import io.katharsis.resource.Document;

public class TestRoleApi  extends KatharsisBase {
	
	private List<RoleDto> originRoles;
	
	private String role1 = "ROLE_USER_T1";
	
	
	@Before
	public void b() throws JsonParseException, JsonMappingException, IOException {
		ResponseEntity<String> response = requestForBody(jwt1, getBaseURI());
		String body = response.getBody();
		originRoles = getList(body, RoleDto.class);
		Optional<RoleDto> ro = originRoles.stream().filter(r -> role1.equals(r.getName())).findAny(); 
		if (ro.isPresent()) {
			deleteByExchange(jwt1, getItemUrl(ro.get().getId()));
			originRoles.remove(ro.get());
		}
	}
	
	
	@Test
	public void tAddOneNoName() throws JsonParseException, JsonMappingException, IOException {
		ResponseEntity<String> response = postItemWithExplicitFixtures("rolenoname", jwt1);
		writeDto(response, getResourceName(), ActionNames.POST_ERROR);
		Document d = toDocument(response.getBody());
		List<ErrorData> eds = d.getErrors();
		assertThat(eds.size(), equalTo(1));
		assertThat(eds.get(0).getStatus(), equalTo(String.valueOf(HttpStatus.UNPROCESSABLE_ENTITY)));
	}
	
	@Test
	public void tAdmionAddOne() throws JsonParseException, JsonMappingException, IOException {
		ResponseEntity<String> response = postItem(jwt1);
		response.getHeaders().containsKey(JwtUtil.REFRESH_HEADER_NAME);
		writeDto(response, getResourceName(), ActionNames.POST_RESULT);
		assertThat(response.getStatusCodeValue(), equalTo(HttpStatus.CREATED.value()));
		RoleDto newRole = getOne(response.getBody(), RoleDto.class);
		assertThat(newRole.getName(), equalTo(role1));
		// again
		response = postItem(jwt1);
		String body = response.getBody();
		
		Document d = toDocument(body);
		assertThat(d.getErrors().get(0).getCode(), equalTo("-104"));
		assertThat(response.getStatusCodeValue(), equalTo(AppExceptionMapper.APP_ERROR_STATUS_CODE));
		response = requestForBody(jwt1, getBaseURI());
		writeDto(response, getResourceName(), ActionNames.GET_LIST);
		response = requestForBody(jwt1, getItemUrl(newRole.getId()));
		writeDto(response, getResourceName(), ActionNames.GET_ONE);
		deleteByExchange(jwt1, getItemUrl(newRole.getId()));
	}
	
	@Test
	public void tNormalUserAddOne() throws JsonParseException, JsonMappingException, IOException {
		response = postItem(getJwtToken("u1", "123"));
		assertAccessDenied(response);
	}


	@Override
	protected String getResourceName() {
		return JsonApiResourceNames.ROLE;
	}

}
