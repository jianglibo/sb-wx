package com.jianglibo.wx.fixturegen;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jianglibo.wx.JsonApiPostBodyWrapper;
import com.jianglibo.wx.JsonApiPostBodyWrapper.CreateOneBody;
import com.jianglibo.wx.JsonApiPostBodyWrapperBuilder;
import com.jianglibo.wx.JsonApiPostBodyWrapperBuilder.OneObjectWapperBuilder;
import com.jianglibo.wx.KatharsisBase;
import com.jianglibo.wx.config.JsonApiResourceNames;

public class TestApproveFixture extends KatharsisBase {

	@Test
	public void t() throws JsonProcessingException {
		OneObjectWapperBuilder builder = JsonApiPostBodyWrapperBuilder.getObjectRelationBuilder(JsonApiResourceNames.APPROVE);
		
		JsonApiPostBodyWrapper<CreateOneBody> body = builder.addRelation("requester", JsonApiResourceNames.BOOT_USER, 10L)
				.addRelation("receiver", JsonApiResourceNames.BOOT_USER, 20L).build();
		String s = objectMapper.writeValueAsString(body);
		printme(s);
	}

	@Override
	protected String getResourceName() {
		return null;
	}
}
