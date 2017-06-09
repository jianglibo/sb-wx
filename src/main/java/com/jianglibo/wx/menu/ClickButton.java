package com.jianglibo.wx.menu;

public class ClickButton extends MenuButton {
	
	private final String key;
	
	public ClickButton(String name, String key) {
		super(ButtonType.click, name);
		this.key = key;
	}

	public String getKey() {
		return key;
	}

}
