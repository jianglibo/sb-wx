package com.jianglibo.wx.message.event;

import com.jianglibo.wx.message.WxInEvent;

public class ScanEvent extends WxInEvent {
	
	private String ticket;
	
	public ScanEvent() {
		super();
		setEvent(WxEventType.SCAN);
	}

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}
}
