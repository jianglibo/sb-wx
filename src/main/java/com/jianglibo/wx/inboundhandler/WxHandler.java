package com.jianglibo.wx.inboundhandler;

import org.springframework.beans.factory.annotation.Autowired;

import com.jianglibo.wx.message.WxBodyUtil;

public class WxHandler {
	
	@Autowired
	protected WxBodyUtil wxUtil;
}
