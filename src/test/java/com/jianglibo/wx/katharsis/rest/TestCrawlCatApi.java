package com.jianglibo.wx.katharsis.rest;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertThat;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.jianglibo.wx.KatharsisBase;
import com.jianglibo.wx.config.JsonApiResourceNames;
import com.jianglibo.wx.domain.Site;
import com.jianglibo.wx.katharsis.dto.CrawlCatDto;
import com.jianglibo.wx.util.JsonApiUrlBuilder;

public class TestCrawlCatApi  extends KatharsisBase {
	
	private String jwtToken;
	
	@Before
	public void b() throws JsonParseException, JsonMappingException, IOException {
		jwtToken = getAdminJwtToken();
		deleteAllSitesAndCrawlCats();
	}
	
	@Test
	public void tAddOne() throws JsonParseException, JsonMappingException, IOException {
		ResponseEntity<String> response = postItem(jwtToken);
		writeDto(response, getResourceName(), ActionNames.POST_RESULT);
		CrawlCatDto ccd = getOne(response, CrawlCatDto.class);
		assertThat(response.getStatusCodeValue(), equalTo(HttpStatus.CREATED.value()));
		assertThat("id should great than 0.", ccd.getId(), greaterThan(0L));
		
		response = requestForBody(jwtToken, getBaseURI());
		writeDto(response, getResourceName(), ActionNames.GET_LIST);
		
		response = requestForBody(jwtToken, getItemUrl(ccd.getId()));
		writeDto(response, getResourceName(), ActionNames.GET_ONE);
		
		createSite(ccrepository.findOne(ccd.getId()));
		response = requestForBody(jwtToken, new JsonApiUrlBuilder(getItemUrl(ccd.getId())).withInclude("sites").build()); //include sites
		writeDto(response, getResourceName(), ActionNames.GET_ONE_INCLUDE);
		String crawlCatBody = response.getBody();
		String self = getRelationshipsSelf(crawlCatBody, JsonApiResourceNames.SITE);
		response = requestForBody(jwtToken, self);
		writeDto(response, getResourceName(), ActionNames.GET_RELATION_SELF);
		String related = getRelationshipsRelated(crawlCatBody, JsonApiResourceNames.SITE);
		response = requestForBody(jwtToken, related);
		writeDto(response, getResourceName(), ActionNames.GET_RELATION_RELATED);
	}
	
	@Test
	public void notAllowAdd() throws IOException {
		ResponseEntity<String> response = postItem(getNormalJwtToken());
		assertErrors(response);
	}
	
	@Test
	public void notAllowGetList() throws IOException {
		ResponseEntity<String> response = requestForBody(getNormalJwtToken(), getBaseURI());
		assertErrors(response);
	}
	
	@Test
	public void notAllowGetOne() throws IOException {
		Site site = createSite();
		ResponseEntity<String> response = requestForBody(getNormalJwtToken(), getItemUrl(site.getCrawlCat().getId()));
		assertErrors(response);
	}

	
	@Override
	protected String getResourceName() {
		return JsonApiResourceNames.CRAWL_CAT;
	}

}
