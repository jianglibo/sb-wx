package com.jianglibo.wx.katharsis.rest;

import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jianglibo.wx.JsonApiPostBodyWrapper;
import com.jianglibo.wx.JsonApiPostBodyWrapperBuilder;
import com.jianglibo.wx.Tutil;
import com.jianglibo.wx.JsonApiPostBodyWrapper.CreateListBody;
import com.jianglibo.wx.JsonApiPostBodyWrapperBuilder.ListWapperBuilder;
import com.jianglibo.wx.config.JsonApiResourceNames;
import com.jianglibo.wx.domain.BootGroup;
import com.jianglibo.wx.domain.BootUser;

@Component
public class PostUtilFort {
	
	@Autowired
	private Tutil tutil;

	public String createPostPostBody(BootGroup group, BootUser...users) throws JsonProcessingException {
		Long[] ids = Stream.of(users).map(u -> u.getId()).toArray(size -> new Long[size]);
		ListWapperBuilder lb = JsonApiPostBodyWrapperBuilder.getListRelationBuilder(JsonApiResourceNames.POST)
				.addAttributePair("title", "title")
				.addAttributePair("content", "content")
				.addRelation("sharedUsers", JsonApiResourceNames.BOOT_USER, ids);
		if (group != null) {
			lb.addRelation("sharedGroups", JsonApiResourceNames.BOOT_GROUP, group.getId());
		}
				
		JsonApiPostBodyWrapper<CreateListBody> jbw = lb.build();
		return tutil.getObjectMapper().writeValueAsString(jbw);
	}
}
