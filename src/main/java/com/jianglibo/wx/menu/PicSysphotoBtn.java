package com.jianglibo.wx.menu;

public class PicSysphotoBtn extends MenuButton {
	
	private final String key;

	public PicSysphotoBtn(String name, String key) {
		super(ButtonType.pic_sysphoto, name);
		this.key = key;
	}

	public String getKey() {
		return key;
	}

}
