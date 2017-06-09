package com.jianglibo.wx.api;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.jianglibo.wx.WxBase;

public class TestAccessTokenHolder extends WxBase {
	
	@Autowired
	private AccessTokenHolder accessTokenHolder;
	
	@Test
	public void t() {
		String s = accessTokenHolder.getAccessToken();
		printme(s);
		assertNotNull(s);
	}
	

}
