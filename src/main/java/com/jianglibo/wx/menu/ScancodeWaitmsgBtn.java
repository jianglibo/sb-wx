package com.jianglibo.wx.menu;

public class ScancodeWaitmsgBtn extends MenuButton {
	
	private final String key;

	public ScancodeWaitmsgBtn(String name, String key) {
		super(ButtonType.scancode_waitmsg, name);
		this.key = key;
	}

	public String getKey() {
		return key;
	}

}
