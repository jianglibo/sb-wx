package com.jianglibo.wx.menu;

public class LocationSelectBtn extends MenuButton {
	
	private final String key;

	public LocationSelectBtn(String name, String key) {
		super(ButtonType.location_select, name);
		this.key = key;
	}

	public String getKey() {
		return key;
	}

}
