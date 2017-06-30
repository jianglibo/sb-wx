package com.jianglibo.wx.katharsis.rest;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.jianglibo.wx.JsonApiPostBodyWrapper;
import com.jianglibo.wx.JsonApiPostBodyWrapper.CreateOneBody;
import com.jianglibo.wx.JsonApiPostBodyWrapperBuilder;
import com.jianglibo.wx.KatharsisBase;
import com.jianglibo.wx.config.JsonApiResourceNames;
import com.jianglibo.wx.constant.AppErrorCodes;
import com.jianglibo.wx.domain.BootGroup;
import com.jianglibo.wx.domain.BootUser;
import com.jianglibo.wx.repository.ApproveRepository;
import com.jianglibo.wx.repository.BootGroupRepository;

import io.katharsis.errorhandling.ErrorData;

public class TestApproveAdd extends KatharsisBase {
	
	@Autowired
	private BootGroupRepository groupRepo;
	
	@Autowired
	private ApproveRepository approveRepo;
	
	@Before
	public void b() throws JsonParseException, JsonMappingException, IOException {
		initTestUser();
		approveRepo.deleteAll();
		groupRepo.deleteAll();
	}
	
	@Test
	public void t() throws IOException {
		BootUser requester = tutil.createBootUser("b1", "123");
		BootUser receiver = tutil.createBootUser("b2", "123");
		BootGroup bg = new BootGroup("agroup");
		bg = groupRepo.save(bg);
		
		JsonApiPostBodyWrapper<CreateOneBody> jpw = JsonApiPostBodyWrapperBuilder.getObjectRelationBuilder(getResourceName()).addAttributePair("targetType", BootGroup.class.getName())
				.addAttributePair("targetId", bg.getId())
				.addRelation("requester", JsonApiResourceNames.BOOT_USER, requester.getId())
				.addRelation("receiver", JsonApiResourceNames.BOOT_USER, receiver.getId()).build();
		
		String s = indentOm.writeValueAsString(jpw);
		response = postItemWithContent(s, jwt1);
		List<ErrorData> eds = getErrors(response);
		assertThat(eds.size(), equalTo(1));
		assertThat(eds.get(0).getCode(), equalTo(AppErrorCodes.UNSUPPORTED_REQUEST));
	}

	@Override
	protected String getResourceName() {
		return JsonApiResourceNames.APPROVE;
	}

}
