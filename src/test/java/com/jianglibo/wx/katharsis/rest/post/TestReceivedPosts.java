package com.jianglibo.wx.katharsis.rest.post;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.jianglibo.wx.KatharsisBase;
import com.jianglibo.wx.config.JsonApiResourceNames;
import com.jianglibo.wx.domain.Post;
import com.jianglibo.wx.domain.PostShare;
import com.jianglibo.wx.repository.PostShareRepository;

public class TestReceivedPosts extends KatharsisBase {
	
	@Autowired
	private PostShareRepository psRepo;
	
	
	@Before
	public void b() throws JsonParseException, JsonMappingException, IOException {
		initTestUser();
	}
	
	@Test
	public void t() throws IOException {
		Post post = new Post();
		post.setTitle("title");
		post.setContent("content");
		post.setCreator(user0);
		postRepo.save(post);

		PostShare ps = new PostShare(post, user1);
		psRepo.save(ps);

		response = requestForBody(jwt1, getItemUrl(user1.getId()) + "/receivedPosts");
		
		writeDto(response, JsonApiResourceNames.BOOT_USER, "getreceived");
		assertItemNumber(response, Post.class, 1);
	}

	@Override
	protected String getResourceName() {
		return JsonApiResourceNames.BOOT_USER;
	}

}
