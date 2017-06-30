package com.jianglibo.wx.katharsis.rest;


import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.jianglibo.wx.KatharsisBase;
import com.jianglibo.wx.Tutil;
import com.jianglibo.wx.config.JsonApiResourceNames;
import com.jianglibo.wx.domain.BootUser;
import com.jianglibo.wx.domain.Post;
import com.jianglibo.wx.domain.Unread;
import com.jianglibo.wx.katharsis.dto.UnreadDto;
import com.jianglibo.wx.repository.UnreadRepository;
import com.jianglibo.wx.util.MyJsonApiUrlBuilder;

public class TestUserUnreadPost  extends KatharsisBase {
	
	@Autowired
	private UnreadRepository unreadRepo;
	
	@Autowired
	private PostUtilFort postUtil;
	
	@Before
	public void b() throws JsonParseException, JsonMappingException, IOException {
		deleteAllUsers();
		unreadRepo.deleteAll();
		jwtToken = getAdminJwtToken();
	}
	
	@Test
	public void tAddOne() throws JsonParseException, JsonMappingException, IOException {
		BootUser user1 = tutil.createUser1();
		BootUser user2 = tutil.createUser2();
		
		String pbody = postUtil.createPostPostBody(null, user1, user2);
		
		String jwt1 = getJwtToken(Tutil.USER_1, Tutil.PASSWORD);
		postItemWithContent(pbody, jwt1, getBaseURI(JsonApiResourceNames.POST));
		
	}


	@Override
	protected String getResourceName() {
		return JsonApiResourceNames.BOOT_USER;
	}

}
