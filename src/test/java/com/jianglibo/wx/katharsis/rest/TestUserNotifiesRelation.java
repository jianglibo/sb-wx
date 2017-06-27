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
import com.jianglibo.wx.KatharsisBase;
import com.jianglibo.wx.config.JsonApiResourceNames;
import com.jianglibo.wx.domain.BootUser;
import com.jianglibo.wx.domain.MessageNotify;
import com.jianglibo.wx.katharsis.dto.MessageNotifyDto;
import com.jianglibo.wx.repository.MessageNotifyRepository;

public class TestUserNotifiesRelation  extends KatharsisBase {
	
	@Autowired
	private MessageNotifyRepository mnRepo;
	
	@Before
	public void b() throws JsonParseException, JsonMappingException, IOException {
		deleteAllUsers();
		jwtToken = getAdminJwtToken();
	}
	
	@Test
	public void t() throws Exception {
		BootUser follower = createBootUser("hello", "hello");
		MessageNotify mn = new MessageNotify();
		mn.setBootUser(follower);
		mn.setNtype(MessageNotify.POST_NTYPE);
		mn = mnRepo.save(mn);
		response = requestForBody(jwtToken, getItemUrl(follower.getId()) + "/notifies");
		writeDto(response.getBody(), getResourceName(), "notifiesrelation");
		List<MessageNotifyDto> notifies = getList(response, MessageNotifyDto.class);
		assertThat(notifies.size(), equalTo(1));
		assertThat(notifies.get(0).getNtype(), equalTo(MessageNotify.POST_NTYPE));
		assertThat(notifies.get(0).getNumber(), equalTo(0));
	}
	

	@Override
	protected String getResourceName() {
		return JsonApiResourceNames.BOOT_USER;
	}

}