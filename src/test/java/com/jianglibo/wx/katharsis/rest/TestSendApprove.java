package com.jianglibo.wx.katharsis.rest;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.jianglibo.wx.JsonApiListBodyWrapper;
import com.jianglibo.wx.KatharsisBase;
import com.jianglibo.wx.config.JsonApiResourceNames;
import com.jianglibo.wx.domain.Approve;
import com.jianglibo.wx.domain.BootGroup;
import com.jianglibo.wx.domain.BootUser;
import com.jianglibo.wx.repository.ApproveRepository;
import com.jianglibo.wx.repository.BootGroupRepository;

public class TestSendApprove extends KatharsisBase {
	
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
		
		Approve a1 = new Approve.ApproveBuilder<BootGroup>(bg).sender(bu).receiver(b1).build();
		approveRepo.save(a1);
		
		response = requestForBody(jwtToken, getItemUrl(a1.getId()));
		writeDto(response, getResourceName(), ActionNames.GET_ONE);
		
		// test sparse fields.
		response = requestForBody(jwtToken, getItemUrl(a1.getId()) + new SparseFieldBuilder("?").includes("requester","receiver").resouceFields("users", "id").build());
		writeDto(response, getResourceName(), ActionNames.GET_ONE);
		
		JsonApiListBodyWrapper jbw = new JsonApiListBodyWrapper("approves", b1.getId());
		
//		response = requestForBody(jwtToken, getItemUrl(b1.getId()) + "/receivedPosts");
//		
//		writeDto(response, JsonApiResourceNames.BOOT_USER, "getreceived");
//		assertItemNumber(response, Post.class, 1);
	}

	@Override
	protected String getResourceName() {
		return JsonApiResourceNames.APPROVE;
	}

}
