package com.jianglibo.wx.menu;

public class ScancodePushBtn extends MenuButton {
	
	private final String key;

	public ScancodePushBtn(String name, String key) {
		super(ButtonType.scancode_push, name);
		this.key = key;
	}

	public String getKey() {
		return key;
	}

}
