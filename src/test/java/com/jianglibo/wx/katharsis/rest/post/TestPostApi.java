package com.jianglibo.wx.katharsis.rest.post;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;

import org.apache.http.HttpResponse;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.jianglibo.wx.JsonApiPostBodyWrapper;
import com.jianglibo.wx.JsonApiPostBodyWrapperBuilder;
import com.jianglibo.wx.KatharsisBase;
import com.jianglibo.wx.JsonApiPostBodyWrapper.CreateListBody;
import com.jianglibo.wx.config.JsonApiResourceNames;
import com.jianglibo.wx.domain.BootGroup;
import com.jianglibo.wx.domain.BootUser;
import com.jianglibo.wx.domain.GroupUserRelation;
import com.jianglibo.wx.domain.Post;
import com.jianglibo.wx.jwt.JwtUtil;
import com.jianglibo.wx.katharsis.dto.MediumDto;
import com.jianglibo.wx.katharsis.dto.PostDto;
import com.jianglibo.wx.katharsis.dto.UserDto;
import com.jianglibo.wx.repository.GroupUserRelationRepository;
import com.jianglibo.wx.repository.UnreadRepository;

public class TestPostApi  extends KatharsisBase {
	
	@Autowired
	private GroupUserRelationRepository guRepo;
	
	@Autowired
	private UnreadRepository unreadRepo;
	
	@Before
	public void b() throws JsonParseException, JsonMappingException, IOException {
		initTestUser();
	}

	@Test
	public void tAddOneByForm() throws JsonParseException, JsonMappingException, IOException {
		HttpResponse apacheResponse = uploadFile(jwt1, Paths.get("fixturesingit", "v.js"));
		String url = apacheResponse.getFirstHeader("location").getValue();
		response = requestForBody(jwt1, url);
		
		MediumDto m = getList(response, MediumDto.class).get(0);
		
		BootUser b3 = tutil.createBootUser("b3", "123");
		BootUser b4 = tutil.createBootUser("b4", "123");
		
		BootGroup bg = new BootGroup("group");
		bg.setCreator(user1);
		groupRepo.save(bg);
		
		GroupUserRelation gur = new GroupUserRelation(bg, b3);
		guRepo.save(gur);
		
		gur = new GroupUserRelation(bg, b4);
		guRepo.save(gur);

		apacheResponse = postPost(jwt1, "atitle", "acontent"
				,Arrays.asList(m.getId())
				,Arrays.asList(user1.getId(), user2.getId())
				,Arrays.asList(bg.getId())
				,Paths.get("fixturesingit", "v.js"));
		
		url = apacheResponse.getFirstHeader("location").getValue();
		response = requestForBody(jwt1, url);
		writeDto(response, getResourceName(), "formpost-result");
		PostDto pd = getOne(response, PostDto.class);
		assertItemNumber(response, PostDto.class, 1);
		
		assertThat(unreadRepo.count(), equalTo(4L));
		
		response = requestForBody(jwt1, getItemUrl(pd.getId()) + "/sharedUsers");
		assertItemNumber(response, UserDto.class, 4);
	}	
	
	@Test
	public void tAddOneByRest() throws JsonParseException, JsonMappingException, IOException {
		
		HttpResponse apacheResponse = uploadFile(jwt1, Paths.get("fixturesingit", "v.js"));
		String url = apacheResponse.getFirstHeader("location").getValue();
		response = requestForBody(jwt1, url);
		
		MediumDto m = getList(response, MediumDto.class).get(0);
		
		BootUser b3 = tutil.createBootUser("b3", "123");
		BootUser b4 = tutil.createBootUser("b4", "123");
		
		BootGroup bg = new BootGroup("group");
		bg.setCreator(user1);
		groupRepo.save(bg);
		
		GroupUserRelation gur = new GroupUserRelation(bg, b3);
		guRepo.save(gur);
		
		gur = new GroupUserRelation(bg, b4);
		guRepo.save(gur);
		
		// we have a group with 2 members.
		
		JsonApiPostBodyWrapper<CreateListBody> jbw = JsonApiPostBodyWrapperBuilder.getListRelationBuilder(getResourceName())
				.addAttributePair("title", "title")
				.addAttributePair("content", "content")
				.addRelation("media", JsonApiResourceNames.MEDIUM, m.getId())
				.addRelation("sharedUsers", JsonApiResourceNames.BOOT_USER, user1.getId(),user2.getId())
				.addRelation("sharedGroups", JsonApiResourceNames.BOOT_GROUP, bg.getId())
				.build();
		
		
		String s = objectMapper.writeValueAsString(jbw);
		writeDto(s, getResourceName(), "postcontent");
		ResponseEntity<String> response = postItemWithContent(s, jwt1);
		
		response.getHeaders().containsKey(JwtUtil.REFRESH_HEADER_NAME);
		writeDto(response, getResourceName(), ActionNames.POST_RESULT);
		PostDto newPost = getOne(response.getBody(), PostDto.class);
		assertThat(newPost.getTitle(), equalTo("title"));
		
		
		response = requestForBody(jwt1, getItemUrl(newPost.getId()) + "/sharedUsers");
		assertItemNumber(response, UserDto.class, 4);
		
		
		// get all posts, when no filter given toAll filter is added. toAll attribute of this post is false, So no item should be return.
		response = requestForBody(jwt1, getBaseURI());
		assertItemNumber(response, PostDto.class, 0);
		
		Post p = postRepo.findOne(newPost.getId());
		p.setToAll(true);
		postRepo.save(p);
		response = requestForBody(jwt1, getBaseURI());
		assertItemNumber(response, PostDto.class, 1);
		
		// get this post
		response = requestForBody(jwt1, getItemUrl(newPost.getId()));
		writeDto(response, getResourceName(), ActionNames.GET_ONE);
		
		// get this post include media
		response = requestForBody(jwt1, getItemUrl(newPost.getId()) + "?include=media");
		writeDto(response, getResourceName(), ActionNames.GET_ONE_INCLUDE);
		PostDto post = getOne(response, PostDto.class);
		assertThat(post.getMedia().size(), equalTo(1));
		
		deleteByExchange(jwt1, getItemUrl(newPost.getId()));
	}


	@Override
	protected String getResourceName() {
		return JsonApiResourceNames.POST;
	}
}
