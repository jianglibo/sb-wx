package com.jianglibo.wx.inboundhandler;

import org.springframework.stereotype.Component;

import com.jianglibo.wx.message.WxInMessage;

@Component
public class InMessageHandler {
	
	public String handle(WxInMessage message) {
		switch (message.getMsgType()) {
		case image:
			break;
		case link:
			break;
		case location:
			break;
		case shortvideo:
			break;
		case text:
			break;
		case video:
			break;
		case voice:
			break;
		default:
			break;
		}
		return null;
	}
}
