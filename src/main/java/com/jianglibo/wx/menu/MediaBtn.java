package com.jianglibo.wx.menu;

public class MediaBtn extends MenuButton {
	
	private final String media_id;

	public MediaBtn(String name, String media_id) {
		super(ButtonType.media_id, name);
		this.media_id = media_id;
	}

	public String getMedia_id() {
		return media_id;
	}

}
