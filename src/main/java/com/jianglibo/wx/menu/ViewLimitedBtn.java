package com.jianglibo.wx.menu;

public class ViewLimitedBtn extends MenuButton {
	
	private final String media_id;

	public ViewLimitedBtn(String name, String media_id) {
		super(ButtonType.view_limited, name);
		this.media_id = media_id;
	}

	public String getMedia_id() {
		return media_id;
	}

}
