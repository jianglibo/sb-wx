package com.jianglibo.wx.menu;

public class PicWeixinBtn extends MenuButton {
	
	private final String key;

	public PicWeixinBtn(String name, String key) {
		super(ButtonType.pic_weixin, name);
		this.key = key;
	}

	public String getKey() {
		return key;
	}

}
