package com.jianglibo.wx.api;

import java.io.IOException;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.jianglibo.wx.WxBase;
import com.jianglibo.wx.api.AutoReplyRule.AutoReplySetting;

public class TestAutoReplyRule extends WxBase {
	
	@Autowired
	private AutoReplyRule aru;
	
	@Test
	public void t() throws JsonParseException, JsonMappingException, IOException {
		String s = aru.getAutoReplySettingString();
		printme(s);
	}

}
