package com.jianglibo.wx.api.util;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.jianglibo.wx.WxBase;

public class TestMyVerify extends WxBase {
	
//	signature: daa35cebd01b112931e7a6a1b960a9ad5383b6f6timestamp: 1497313081nonce: 2797966136echostr: 6347584983506354171
	
	@Test
	public void t() throws AesException {
		String verifyMsgSig = "daa35cebd01b112931e7a6a1b960a9ad5383b6f6";
		String timeStamp = "1497313081";
		String nonce = "2797966136";
		String echoStr = "6347584983506354171";
		String sha1 = MySHA1.getSHA1(applicationConfig.getMiniAppApiToken(), timeStamp, nonce);
		
		assertThat(sha1, equalTo(verifyMsgSig));
	}

}
