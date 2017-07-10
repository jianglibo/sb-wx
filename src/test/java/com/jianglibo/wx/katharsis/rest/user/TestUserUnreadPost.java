package com.jianglibo.wx.katharsis.rest.user;


import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.jianglibo.wx.KatharsisBase;
import com.jianglibo.wx.config.JsonApiResourceNames;
import com.jianglibo.wx.domain.Post;
import com.jianglibo.wx.katharsis.dto.MessageNotifyDto;
import com.jianglibo.wx.katharsis.dto.PostDto;
import com.jianglibo.wx.katharsis.dto.UnreadDto;
import com.jianglibo.wx.katharsis.rest.PostUtilFort;
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
		deleteAllPost();
		unreadRepo.deleteAll();
		initTestUser();
	}
	
	@Test
	public void tAddOne() throws JsonParseException, JsonMappingException, IOException {
		String pbody = postUtil.createPostPostBody(null, user2);
		
		// the creator is user1.
		postItemWithContent(pbody, jwt1, getBaseURI(JsonApiResourceNames.POST));
		
		// for user2, there is an unread post now, but not shown by unread.
		MyJsonApiUrlBuilder mab = new MyJsonApiUrlBuilder("?").filters("type", Post.class.getSimpleName());
		response = requestForBody(jwt2, getItemUrl(user2.getId()) + "/unreads" + mab.build());
		assertItemNumber(response, UnreadDto.class, 0);

		//it is shown by MessageNotify.
		response = requestForBody(jwt2, getItemUrl(user2.getId()) + "/notifies");
		List<MessageNotifyDto> mn = getList(response, MessageNotifyDto.class);
		assertThat(mn.size(), equalTo(1));
		assertThat(mn.get(0).getNumber(), equalTo(1));
		assertThat(mn.get(0).getNtype(), equalTo(Post.class.getSimpleName()));
		
		// get received posts.
		response = requestForBody(jwt2, getItemUrl(user2.getId()) + "/receivedPosts");
		PostDto post = getList(response, PostDto.class).get(0);
		// from user2's view, it's not been read.
		assertFalse(post.isRead());
		
		// after access the url.
		response = requestForBody(jwt2, getItemUrl(JsonApiResourceNames.POST, post.getId()));
		post = getOne(response, PostDto.class);
		assertTrue(post.isRead());
		
		// test again.
		response = requestForBody(jwt2, getItemUrl(user2.getId()) + "/receivedPosts");
		post = getList(response, PostDto.class).get(0);
		// from user2's view, it's not been read.
		assertTrue(post.isRead());
	}


	@Override
	protected String getResourceName() {
		return JsonApiResourceNames.BOOT_USER;
	}

}
