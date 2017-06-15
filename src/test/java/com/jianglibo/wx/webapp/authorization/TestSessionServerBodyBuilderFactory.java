package com.jianglibo.wx.webapp.authorization;

import java.io.IOException;

import org.junit.Test;

import com.jianglibo.wx.WxBase;
import com.jianglibo.wx.webapp.authorization.SessionServerBodyBuilderFactory.AuthBodyBuilder;

public class TestSessionServerBodyBuilderFactory extends WxBase {
	
	@Test
	public void t() throws IOException {
		AuthBodyBuilder abb =  SessionServerBodyBuilderFactory.getAuthBodyBuilder();
		SessionServerPostBody body = abb.withId("id").withSkey("skey").build();
		String s = objectMapper.writeValueAsString(body);
		printme(s);
		jsonAssertUtil().hasKey(s, "interface");
	}
	
//	curl -i -d '{"version":1,"componentName":"MA","interface":{"interfaceName":"qcloud.cam.auth","para":{"skey":"c6160db685fe3f1491b2ed8cdaa7d24d","id":"9837c77505c2f51b2d766b25769eeaaf"}}}' https://jianglibo.com/mina_auth/

}
