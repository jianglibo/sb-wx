package com.jianglibo.wx.katharsis.rest;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.io.IOException;

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
import com.jianglibo.wx.domain.BootGroup;
import com.jianglibo.wx.domain.BootUser;
import com.jianglibo.wx.katharsis.dto.ApproveDto;
import com.jianglibo.wx.repository.ApproveRepository;
import com.jianglibo.wx.repository.BootGroupRepository;

public class TestApproveAdd extends KatharsisBase {
	
	@Autowired
	private BootGroupRepository groupRepo;
	
	@Autowired
	private ApproveRepository approveRepo;
	
	@Before
	public void b() throws JsonParseException, JsonMappingException, IOException {
		deleteAllUsers();
		approveRepo.deleteAll();
		groupRepo.deleteAll();
		jwtToken = getAdminJwtToken();
	}
	
	@Test
	public void t() throws IOException {
		BootUser bu = bootUserRepo.findByName("admin");
		BootUser b1 = createBootUser("b1", "123");
		BootGroup bg = new BootGroup("agroup");
		bg = groupRepo.save(bg);
		
		JsonApiPostBodyWrapper<CreateOneBody> jpw = JsonApiPostBodyWrapperBuilder.getObjectRelationBuilder(getResourceName()).addAttributePair("targetType", BootGroup.class.getName())
				.addAttributePair("targetId", bg.getId())
				.addRelation("requester", JsonApiResourceNames.BOOT_USER, bu.getId())
				.addRelation("receiver", JsonApiResourceNames.BOOT_USER, b1.getId()).build();
		
		String s = indentOm.writeValueAsString(jpw);
		writeDto(s, getResourceName(), "requestjoingroup");
		response = postItemWithContent(s, jwtToken);
		assertResponseCode(response, 201);
		ApproveDto ad = getOne(response, ApproveDto.class);
		assertThat(ad.getTargetId(), equalTo(bg.getId()));
		assertThat(ad.getTargetType(), equalTo(BootGroup.class.getName()));
		
		response = requestForBody(jwtToken, getItemUrl(JsonApiResourceNames.BOOT_USER, b1.getId()) + "/receivedApproves");
		ad = getOne(response, ApproveDto.class);
		assertThat(ad.getTargetId(), equalTo(bg.getId()));
		assertThat(ad.getTargetType(), equalTo(BootGroup.class.getName()));
		
		
		response = requestForBody(jwtToken, getItemUrl(JsonApiResourceNames.BOOT_USER, bu.getId()) + "/receivedApproves");
		assertItemNumber(response, ApproveDto.class, 0);

	}

	@Override
	protected String getResourceName() {
		return JsonApiResourceNames.APPROVE;
	}

}
