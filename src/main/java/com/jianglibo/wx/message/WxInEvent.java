package com.jianglibo.wx.message;

public abstract class WxInEvent extends WxBody {

	public static enum WxEventType {
		subscribe, unsubscribe, SCAN, LOCATION, CLICK, VIEW
	}
	
	public WxInEvent() {
		super();
		setMsgType(WxMessageType.event);
	}
	
	private WxEventType event;
	private String eventKey;
	

	public WxEventType getEvent() {
		return event;
	}
	public void setEvent(WxEventType event) {
		this.event = event;
	}
	public String getEventKey() {
		return eventKey;
	}
	public void setEventKey(String eventKey) {
		this.eventKey = eventKey;
	}
	
	
	
	
}
