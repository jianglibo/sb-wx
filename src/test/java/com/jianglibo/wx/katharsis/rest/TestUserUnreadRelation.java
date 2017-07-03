package com.jianglibo.wx.katharsis.rest;


import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.jianglibo.wx.KatharsisBase;
import com.jianglibo.wx.config.JsonApiResourceNames;
import com.jianglibo.wx.domain.Post;
import com.jianglibo.wx.domain.Unread;
import com.jianglibo.wx.katharsis.dto.UnreadDto;
import com.jianglibo.wx.repository.UnreadRepository;
import com.jianglibo.wx.util.MyJsonApiUrlBuilder;

public class TestUserUnreadRelation  extends KatharsisBase {
	
	@Autowired
	private UnreadRepository unreadRepo;
	
	@Before
	public void b() throws JsonParseException, JsonMappingException, IOException {
		deleteAllUsers();
		unreadRepo.deleteAll();
		initTestUser();
	}
	
	@Test
	public void tAddOne() throws JsonParseException, JsonMappingException, IOException {
		Unread ur = new Unread();
		ur.setObid(55L);
		ur.setType(Post.class.getSimpleName());
		ur.setBootUser(user1);
		unreadRepo.save(ur);
		
		response = requestForBody(jwt1, getItemUrl(user1.getId()) + "/unreads");
		writeDto(response.getBody(), getResourceName(), "unreadrelation");
		assertItemNumber(response, UnreadDto.class, 0);
		
		String filterUrl = new MyJsonApiUrlBuilder("?").filters("type", Post.class.getSimpleName()).build();
		response = requestForBody(jwt1, getItemUrl(user1.getId()) + "/unreads" + filterUrl);
		writeDto(response.getBody(), getResourceName(), "unreadrelation");
		assertItemNumber(response, UnreadDto.class, 1);
	}


	@Override
	protected String getResourceName() {
		return JsonApiResourceNames.BOOT_USER;
	}

}
