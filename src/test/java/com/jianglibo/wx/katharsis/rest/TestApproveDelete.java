package com.jianglibo.wx.katharsis.rest;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.jianglibo.wx.KatharsisBase;
import com.jianglibo.wx.config.JsonApiResourceNames;
import com.jianglibo.wx.domain.Approve;
import com.jianglibo.wx.domain.Approve.ApproveBuilder;
import com.jianglibo.wx.domain.BootGroup;
import com.jianglibo.wx.domain.BootUser;
import com.jianglibo.wx.katharsis.dto.ApproveDto;
import com.jianglibo.wx.repository.ApproveRepository;
import com.jianglibo.wx.repository.BootGroupRepository;

/**
 * no need. Approves don't need to delete.
 * @author Administrator
 *
 */
public class TestApproveDelete extends KatharsisBase {
	
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
		
		Approve ad = new ApproveBuilder<BootGroup>(bg).receiver(b1).sender(bu).build();
		ad = approveRepo.save(ad);
		
		response = requestForBody(jwtToken, getItemUrl(JsonApiResourceNames.BOOT_USER, b1.getId()) + "/receivedApproves");
		ApproveDto addto = getOne(response, ApproveDto.class);
		assertThat(addto.getTargetId(), equalTo(bg.getId()));
		assertThat(addto.getTargetType(), equalTo(BootGroup.class.getName()));
		
//		JsonApiListBodyWrapper jw = new JsonApiListBodyWrapper(JsonApiResourceNames.APPROVE, ad.getId());
//		
//		deleteRelationWithContent(indentOm.writeValueAsString(jw), "receivedApproves", b1.getId(), jwtToken);
//		response = requestForBody(jwtToken, getItemUrl(JsonApiResourceNames.BOOT_USER, b1.getId()) + "/receivedApproves");
//		assertItemNumber(response, ApproveDto.class, 0);
//		
//		assertThat(approveRepo.count(), equalTo(0L));
	}

	@Override
	protected String getResourceName() {
		return JsonApiResourceNames.BOOT_USER;
	}

}
