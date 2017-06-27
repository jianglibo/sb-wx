package com.jianglibo.wx.katharsis.rest;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.jianglibo.wx.JsonApiPostBodyWrapper;
import com.jianglibo.wx.JsonApiPostBodyWrapperBuilder;
import com.jianglibo.wx.KatharsisBase;
import com.jianglibo.wx.JsonApiPostBodyWrapper.CreateListBody;
import com.jianglibo.wx.config.JsonApiResourceNames;
import com.jianglibo.wx.domain.BootUser;
import com.jianglibo.wx.domain.FollowRelation;
import com.jianglibo.wx.domain.Post;
import com.jianglibo.wx.katharsis.dto.PostDto;
import com.jianglibo.wx.katharsis.dto.UserDto;
import com.jianglibo.wx.repository.FollowRelationRepository;

public class TestUserApi  extends KatharsisBase {
	
	@Autowired
	private FollowRelationRepository frRepo;
	
	@Before
	public void b() throws JsonParseException, JsonMappingException, IOException {
		deleteAllUsers();
		jwtToken = getAdminJwtToken();
	}
	
	@Test
	public void tAddOne() throws JsonParseException, JsonMappingException, IOException {
		long c= bootUserRepo.count();
		assertThat(c, equalTo(1L));
		
		BootUser bu = createBootUser("b1", "123", "a", "b", "c");
		
		Long[] roleids = bu.getRoles().stream().map(r -> r.getId()).toArray(size -> new Long[size]);
		
		JsonApiPostBodyWrapper<CreateListBody> jbw = JsonApiPostBodyWrapperBuilder.getListRelationBuilder(getResourceName())
				.addAttributePair("name", "name1334")
				.addAttributePair("email", "ab@c.com")
				.addAttributePair("mobile", "13777777777")
				.addAttributePair("password", "akka123456")
				.addRelation("roles", JsonApiResourceNames.ROLE, roleids).build();
		
		String s = indentOm.writeValueAsString(jbw);
		writeDto(s, JsonApiResourceNames.BOOT_USER, "postcontent");
		
		ResponseEntity<String> response = postItemWithContent(s, jwtToken);
		
		writeDto(response, getResourceName(), ActionNames.POST_RESULT);
		assertThat(response.getStatusCodeValue(), equalTo(HttpStatus.CREATED.value()));
		UserDto newUser = getOne(response.getBody(), UserDto.class);
		
		BootUser u = bootUserRepo.findOne(newUser.getId());
		assertThat(u.getRoles().size(), equalTo(4));
		
		assertThat(newUser.getName(), equalTo("name1334"));
		assertTrue("password should be empty.", newUser.getPassword() == null || newUser.getPassword().isEmpty());
		assertThat("id should great than 0.", newUser.getId(), greaterThan(0L));
		response = requestForBody(jwtToken, getItemUrl(newUser.getId()));
		writeDto(response, getResourceName(), ActionNames.GET_ONE);
		
		response = deleteByExchange(jwtToken, getItemUrl(newUser.getId()));
		assertThat(bootUserRepo.count(), equalTo(c + 1));
		
		
	}
	
	@Test
	public void tGet() throws JsonParseException, JsonMappingException, IOException {
		ResponseEntity<String> response = requestForBody(jwtToken, getBaseURI());
		writeDto(response, getResourceName(), ActionNames.GET_LIST);
	}
	
	@Test
	public void tDeleteSelf() throws JsonParseException, JsonMappingException, IOException {
		BootUser bu = bootUserRepo.findByName("admin");
		ResponseEntity<String> response = deleteByExchange(jwtToken, getItemUrl(bu.getId()));
		getErrors(response);
	}
	
	@Test
	public void getFollow2mesRelationsSelf() throws Exception {
		BootUser bu = loginAsAdmin();
		BootUser follower = createBootUser("hello", "hello");
		FollowRelation fr = new FollowRelation(follower, bu);
		fr = frRepo.save(fr);
		
		response = requestForBody(jwtToken, getItemUrl(bu.getId()) + "/followers");
		writeDto(response, JsonApiResourceNames.BOOT_USER, "getfollow2mesrelations-related");
		List<UserDto> frdtos = getList(response, UserDto.class);
		assertThat(frdtos.size(), equalTo(1));
		
		response = requestForBody(jwtToken, getItemUrl(bu.getId()) + "/followeds");
		writeDto(response, JsonApiResourceNames.BOOT_USER, "getfollow2mesrelations-related");
		frdtos = getList(response, UserDto.class);
		assertThat(frdtos.size(), equalTo(0));
		
		
		response = requestForBody(jwtToken, getItemUrl(follower.getId()) + "/followers");
		writeDto(response, JsonApiResourceNames.BOOT_USER, "getfollow2mesrelations-related");
		frdtos = getList(response, UserDto.class);
		assertThat(frdtos.size(), equalTo(0));

		
		response = requestForBody(jwtToken, getItemUrl(follower.getId()) + "/followeds");
		writeDto(response, JsonApiResourceNames.BOOT_USER, "getfollow2mesrelations-related");
		frdtos = getList(response, UserDto.class);
		assertThat(frdtos.size(), equalTo(1));
	}
	
	@Test
	public void getPostsRelationsSelf() throws Exception {
		BootUser bu = loginAsAdmin();
		Post  post= createPost(bu);
		response = requestForBody(jwtToken, getItemUrl(bu.getId()) + "/relationships/posts");
		writeDto(response, JsonApiResourceNames.BOOT_USER, "getpostsrelations-self");
		List<PostDto> posts = getList(response, PostDto.class);
		assertThat(posts.size(), equalTo(1));
		
		response = requestForBody(jwtToken, getItemUrl(bu.getId()) + "/relationships/posts?page[limit]=1&page[offset]=0");
		writeDto(response, JsonApiResourceNames.BOOT_USER, "getpostsrelations-self");
		posts = getList(response, PostDto.class);
		assertThat(posts.size(), equalTo(1));
	}
	
	@Test
	public void getPostsRelationsRelated() throws Exception {
		BootUser bu = loginAsAdmin();
		Post  post= createPost(bu);
		response = requestForBody(jwtToken, getItemUrl(bu.getId()) + "/posts");
		writeDto(response, JsonApiResourceNames.BOOT_USER, "getpostsrelations-related");
		List<PostDto> posts = getList(response, PostDto.class);
		assertThat(posts.size(), equalTo(1));
	}

	@Override
	protected String getResourceName() {
		return JsonApiResourceNames.BOOT_USER;
	}

}
