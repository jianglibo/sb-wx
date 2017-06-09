﻿package com.jianglibo.wx.katharsis.rest;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.jianglibo.wx.KatharsisBase;
import com.jianglibo.wx.config.JsonApiResourceNames;
import com.jianglibo.wx.domain.CrawlCat;
import com.jianglibo.wx.domain.Site;
import com.jianglibo.wx.katharsis.dto.SiteDto;

import io.katharsis.resource.Document;

public class TestSitetApi  extends KatharsisBase {
	
	private String jwtToken;
	
	@Before
	public void b() throws JsonParseException, JsonMappingException, IOException {
		jwtToken = getAdminJwtToken();
		deleteAllSitesAndCrawlCats();
	}
	
	@Test
	public void tAddOneNoCrawlCat() throws JsonParseException, JsonMappingException, IOException {
		ResponseEntity<String> response = postItemWithExplicitFixtures("sitenocrawlcat", jwtToken);
		printme(response.getBody());
		assertThat(response.getStatusCodeValue(), equalTo(HttpStatus.UNPROCESSABLE_ENTITY.value()));
	}
	
	@Test
	public void tAddOne() throws JsonParseException, JsonMappingException, IOException {
		CrawlCat crawlCat = new CrawlCat();
		crawlCat.setName("acc");
		crawlCat.setDescription("dd");
		crawlCat = ccrepository.save(crawlCat);
		
		String fixture = getFixture(getResourceName());
		Document d = replaceRelationshipId(fixture, "crawlCat", crawlCat.getId());

		ResponseEntity<String> response = postItem(d, jwtToken);
		writeDto(response, getResourceName(), ActionNames.POST_RESULT);
		assertThat(response.getStatusCodeValue(), equalTo(HttpStatus.CREATED.value()));
		verifyRelationships(response,"crawlCat");
		SiteDto sd = getOne(response, SiteDto.class);
		List<SiteDto> sds = getList(response, SiteDto.class);
		assertThat(sds.size(), equalTo(1));
		response = requestForBody(jwtToken, getItemUrl(sd.getId()));
		writeDto(response, getResourceName(), ActionNames.GET_ONE);
		assertNotNull(sd.getCrawlCat());
		
		response = requestForBody(jwtToken, getBaseURI());
		writeDto(response, getResourceName(), ActionNames.GET_LIST);
	}
	
	@Test
	public void getListSort() throws InterruptedException, IOException {
		Site s1 = createSite();
		Site s2 = createSite();
		assertTrue(s2.getUpdatedAt().after(s1.getUpdatedAt()));
		response = requestForBody(jwtToken, getBaseURI() + "?sort=createdAt");
		List<SiteDto> sds = getList(response, SiteDto.class);
		assertTrue(sds.get(1).getCreatedAt().after(sds.get(0).getCreatedAt()));
		
		response = requestForBody(jwtToken, getBaseURI() + "?sort=-createdAt");
		sds = getList(response, SiteDto.class);
		assertTrue(sds.get(0).getCreatedAt().after(sds.get(1).getCreatedAt()));
	}
	
	@Test
	public void normalUserNotAllowGetList() throws IOException {
		createSite();
		response = requestForBody(getJwtToken("loginUser"), getBaseURI() + "?sort=-createdAt");
		assertAccessDenied(response);
	}
	
	@Test
	public void normalUserNotAllowGetOne() throws IOException {
		Site site = createSite();
		response = requestForBody(getJwtToken("loginUser"), getItemUrl(site.getId()));
		assertAccessDenied(response);
	}
	
	@Override
	protected String getResourceName() {
		return JsonApiResourceNames.SITE;
	}

}
