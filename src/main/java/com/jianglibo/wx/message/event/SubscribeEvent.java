package com.jianglibo.wx.message.event;

import com.jianglibo.wx.message.WxInEvent;

public class SubscribeEvent extends WxInEvent {
	
	private String ticket;

	public SubscribeEvent() {
		super();
		setEvent(WxEventType.subscribe);
	}

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}
}
