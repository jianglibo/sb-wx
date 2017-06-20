package com.jianglibo.wx.webapp.authorization;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.Paths;

import org.apache.http.client.ClientProtocolException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.jianglibo.wx.KatharsisBase;
import com.jianglibo.wx.config.ApplicationConfig;
import com.jianglibo.wx.katharsis.dto.MediumDto;
import com.jianglibo.wx.webapp.authorization.FileUploadFilter.FileUploadResponse;

public class TestUpload extends KatharsisBase {
	
	@Autowired
	private ApplicationConfig appConfig;
	
	@Test
	public void t() throws ClientProtocolException, IOException {
		assertTrue("outUrlBase end with /", appConfig.getOutUrlBase().endsWith("/"));
		assertFalse("should not contain {", appConfig.getOutUrlBase().contains("{"));
		assertFalse("should not contain {", appConfig.getUploadLinkBase().contains("{"));
		FileUploadResponse fr = uploadFile(Paths.get("fixturesingit", "v.js"));
		MediumDto m = fr.getMedia().get(0);
		assertThat(m.getContentType(), equalTo("application/octet-stream"));
		assertTrue("should end with .js", m.getOrignName().endsWith(".js"));
		assertTrue("should end with .js", m.getUrl().endsWith(".js"));
	}
	
	@Test
	public void t1() throws ClientProtocolException, IOException {
		FileUploadResponse fr = uploadFile(Paths.get("fixturesingit", "th.jpg"));
		MediumDto m = fr.getMedia().get(0);
		assertThat(m.getContentType(), equalTo("application/octet-stream"));
		assertTrue("should end with .jpg", m.getOrignName().endsWith(".jpg"));
		assertTrue("should end with .jpg", m.getUrl().endsWith(".jpg"));
	}

	@Override
	protected String getResourceName() {
		return null;

	}

}
