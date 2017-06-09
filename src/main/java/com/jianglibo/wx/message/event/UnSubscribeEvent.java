package com.jianglibo.wx.message.event;

import com.jianglibo.wx.message.WxInEvent;

public class UnSubscribeEvent extends WxInEvent {
	
	public UnSubscribeEvent() {
		super();
		setEvent(WxEventType.unsubscribe);
	}
}
