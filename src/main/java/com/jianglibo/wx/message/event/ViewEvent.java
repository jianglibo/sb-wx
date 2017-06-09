package com.jianglibo.wx.message.event;

import com.jianglibo.wx.message.WxInEvent;

public class ViewEvent extends WxInEvent {
	
	private String menuId;
	
	public ViewEvent() {
		super();
		setEvent(WxEventType.VIEW);
	}

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}
}
