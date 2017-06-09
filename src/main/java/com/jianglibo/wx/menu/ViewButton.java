package com.jianglibo.wx.menu;

public class ViewButton extends MenuButton {
	
	private final String url;
	
	public ViewButton(String name, String url) {
		super(ButtonType.click, name);
		this.url = url;
	}

	public String getUrl() {
		return url;
	}

}
