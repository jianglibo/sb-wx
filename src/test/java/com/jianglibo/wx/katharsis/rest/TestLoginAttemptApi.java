package com.jianglibo.wx.katharsis.rest;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.util.List;

import org.junit.Test;
import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.jianglibo.wx.JsonApiPostBodyWrapper;
import com.jianglibo.wx.JsonApiPostBodyWrapperBuilder;
import com.jianglibo.wx.KatharsisBase;
import com.jianglibo.wx.config.JsonApiResourceNames;

import io.katharsis.errorhandling.ErrorData;

public class TestLoginAttemptApi  extends KatharsisBase {
	
	
	@Test
	public void tWrongCredential() throws JsonParseException, JsonMappingException, IOException {
		
		JsonApiPostBodyWrapper<?> jbw = JsonApiPostBodyWrapperBuilder.getObjectRelationBuilder(getResourceName())
				.addAttributePair("username", "admin")
				.addAttributePair("password", "123xisld")
				.build();
		
		String body = indentOm.writeValueAsString(jbw);
		response = postItemWithContent(body, "");
		writeDto(response, getResourceName(), "failed");
		List<ErrorData> eds = getErrors(response);
		assertThat(eds.size(), equalTo(1));
		
		assertThat(response.getStatusCodeValue(), equalTo(HttpStatus.FORBIDDEN.value()));
	}
	
	@Test
	public void tRightCredential() throws JsonParseException, JsonMappingException, IOException {

		
		JsonApiPostBodyWrapper<?> jbw = JsonApiPostBodyWrapperBuilder.getObjectRelationBuilder(getResourceName())
				.addAttributePair("username", "admin")
				.addAttributePair("password", "123456")
				.build();
		String body = indentOm.writeValueAsString(jbw);
		response = postItemWithContent(body, "");
		
		writeDto(response, getResourceName(), "success");
		
		List<ErrorData> eds = getErrors(response);
		assertNull(eds);
		assertThat(response.getStatusCodeValue(), equalTo(HttpStatus.CREATED.value()));
		verifyOneKey(response, "jwtToken", "data", "attributes");
	}

	@Override
	protected String getResourceName() {
		return JsonApiResourceNames.LOGIN_ATTEMPT;
	}

}
