package com.jianglibo.wx.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jianglibo.wx.inboundhandler.InEventHandler;
import com.jianglibo.wx.inboundhandler.InMessageHandler;
import com.jianglibo.wx.message.WxBody;
import com.jianglibo.wx.message.WxBody.WxMessageType;
import com.jianglibo.wx.message.WxBodyUtil;
import com.jianglibo.wx.message.WxInEvent;
import com.jianglibo.wx.message.WxInMessage;

@Controller
public class WxEntrance {
	
	@Autowired
	private WxBodyUtil wxBodyUtil;
	
	@Autowired
	private InEventHandler inEventHandler;
	
	@Autowired
	private InMessageHandler inMessageHandler;

	@RequestMapping(path = "/wxentrance")
	@ResponseBody
	public String dispatch(@RequestBody String body) {
		try {
			WxBody wxbody = wxBodyUtil.deserialize(body);
			WxMessageType wmt = wxbody.getMsgType();
			switch (wmt) {
			case event:
				return inEventHandler.handle((WxInEvent) wxbody);
			default:
				return inMessageHandler.handle((WxInMessage) wxbody);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}
}
