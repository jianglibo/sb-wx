package com.jianglibo.wx.katharsis.rest;

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
import com.jianglibo.wx.domain.BootUser;
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
	}
	
	@Test
	public void tReceivedApproves() throws JsonParseException, JsonMappingException, IOException {
		BootUser bu1 = tutil.createBootUser("b1", "123", "a", "b", "c");
		BootUser bu2 = tutil.createBootUser("b2", "123");
		
		jwtToken = getJwtToken("b1", "123");
		response = requestForBody(jwtToken, getItemUrl(bu1.getId()) + "/receivedApproves");
		assertItemNumber(response, ApproveDto.class, 0);
		
		String jwtToken1 = getJwtToken("b2", "123");
		response = requestForBody(jwtToken1, getItemUrl(bu1.getId()) + "/receivedApproves");
		assertAccessDenied(response);
		
		BootGroup bg = new BootGroup("agroup");
		Approve ap =  new Approve.ApproveBuilder<>(bg).receiver(bu1).sender(bu2).build();
		approveRepo.save(ap);
		
		response = requestForBody(jwtToken, getItemUrl(bu1.getId()) + "/receivedApproves");
		writeDto(response, getResourceName(), "receivedApproves");
		assertItemNumber(response, ApproveDto.class, 1);
		
		
		response = requestForBody(jwtToken1, getItemUrl(bu2.getId()) + "/sentApproves");
		writeDto(response, getResourceName(), "sentApproves");
		assertItemNumber(response, ApproveDto.class, 1);
		ApproveDto dto = getList(response, ApproveDto.class).get(0);
		
		// who can approve join? the receiver bu1.
		
		JsonApiPostBodyWrapper<?> jaw = JsonApiPostBodyWrapperBuilder.getObjectRelationBuilder(JsonApiResourceNames.APPROVE)
				.addAttributePair("state", ApproveState.APPROVED)
				.dtoApplyTo("state")
				.build();
		
		String c = indentOm.writeValueAsString(jaw);
		response = patchByExchange(c, jwtToken, getBaseURI(JsonApiResourceNames.APPROVE) + "/" + dto.getId());
		
		response = patchByExchange(c, jwtToken1, getBaseURI(JsonApiResourceNames.APPROVE) + "/" + dto.getId());
		assertAccessDenied(response);
		
	}
	
	


	@Override
	protected String getResourceName() {
		return JsonApiResourceNames.BOOT_USER;
	}

}
