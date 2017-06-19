package com.jianglibo.wx.webapp.authorization;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
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
	}
	
	@Test
	public void t1() throws ClientProtocolException, IOException {
		FileUploadResponse fr = uploadFile(Paths.get("fixturesingit", "th.jpg"));
		MediumDto m = fr.getMedia().get(0);
		assertThat(m.getContentType(), equalTo("application/octet-stream"));
		assertTrue("should end with .js", m.getOrignName().endsWith(".jpg"));
	}

	
	private FileUploadResponse uploadFile(Path fp) throws IOException {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		String jwtToken = getAdminJwtToken();
		File file = fp.toFile();
		HttpPost post = new HttpPost(appConfig.getOutUrlBase() + "/fileupload");
		FileBody fileBody = new FileBody(file, ContentType.DEFAULT_BINARY);
		StringBody stringBody1 = new StringBody("Message 1", ContentType.MULTIPART_FORM_DATA);
		StringBody stringBody2 = new StringBody("Message 2", ContentType.MULTIPART_FORM_DATA);
		// 
		MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
		builder.addPart("upfile", fileBody);
		builder.addPart("text1", stringBody1);
		builder.addPart("text2", stringBody2);
		HttpEntity entity = builder.build();
		post.setHeader("Authorization", "Bearer " + jwtToken);
		
		//
		post.setEntity(entity);
		HttpResponse response = httpclient.execute(post);
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		response.getEntity().writeTo(os);
		String c = new String(os.toByteArray());
		
		FileUploadResponse m = indentOm.readValue(c, FileUploadResponse.class);
		return m;
	}

	@Override
	protected String getResourceName() {
		// TODO Auto-generated method stub
		return null;

	}

}
