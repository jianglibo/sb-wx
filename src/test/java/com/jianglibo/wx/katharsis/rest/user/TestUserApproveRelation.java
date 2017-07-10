package com.jianglibo.wx.katharsis.rest.user;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.jianglibo.wx.JsonApiPostBodyWrapper;
import com.jianglibo.wx.JsonApiPostBodyWrapperBuilder;
import com.jianglibo.wx.KatharsisBase;
import com.jianglibo.wx.config.JsonApiResourceNames;
import com.jianglibo.wx.domain.Approve;
import com.jianglibo.wx.domain.BootGroup;
import com.jianglibo.wx.eu.ApproveState;
import com.jianglibo.wx.katharsis.dto.ApproveDto;
import com.jianglibo.wx.repository.ApproveRepository;

public class TestUserApproveRelation  extends KatharsisBase {
	
	@Autowired
	private ApproveRepository approveRepo;
	
	@Before
	public void b() throws JsonParseException, JsonMappingException, IOException {
		deleteAllUsers();
		groupRepo.deleteAll();
		initTestUser();
	}
	
	@Test
	public void tReceivedApproves() throws JsonParseException, JsonMappingException, IOException {
		
		response = requestForBody(jwt1, getItemUrl(user1.getId()) + "/receivedApproves");
		assertItemNumber(response, ApproveDto.class, 0);
		
		response = requestForBody(jwt2, getItemUrl(user2.getId()) + "/receivedApproves");
		assertAccessDenied(response);
		
		BootGroup bg = new BootGroup("agroup");
		Approve ap =  new Approve.ApproveBuilder<>(bg).receiver(user1).sender(user2).build();
		approveRepo.save(ap);
		
		response = requestForBody(jwt1, getItemUrl(user1.getId()) + "/receivedApproves");
		writeDto(response, getResourceName(), "receivedApproves");
		assertItemNumber(response, ApproveDto.class, 1);
		
		
		response = requestForBody(jwt2, getItemUrl(user2.getId()) + "/sentApproves");
		writeDto(response, getResourceName(), "sentApproves");
		assertItemNumber(response, ApproveDto.class, 1);
		ApproveDto dto = getList(response, ApproveDto.class).get(0);
		
		// who can approve join? the receiver bu1.
		
		JsonApiPostBodyWrapper<?> jaw = JsonApiPostBodyWrapperBuilder.getObjectRelationBuilder(JsonApiResourceNames.APPROVE)
				.addAttributePair("state", ApproveState.APPROVED)
				.dtoApplyTo("state")
				.build();
		
		String c = indentOm.writeValueAsString(jaw);
		response = patchByExchange(c, jwt1, getBaseURI(JsonApiResourceNames.APPROVE) + "/" + dto.getId());
		
		response = patchByExchange(c, jwt1, getBaseURI(JsonApiResourceNames.APPROVE) + "/" + dto.getId());
		assertAccessDenied(response);
		
	}
	
	


	@Override
	protected String getResourceName() {
		return JsonApiResourceNames.BOOT_USER;
	}

}
