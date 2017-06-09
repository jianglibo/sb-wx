package com.jianglibo.wx.message.event;

import com.jianglibo.wx.message.WxInEvent;

public class ClickEvent extends WxInEvent {
	
	public ClickEvent() {
		super();
		setEvent(WxEventType.CLICK);
	}
}
