package com.jianglibo.wx.menu;

public class MiniProgramButton extends MenuButton {
	
	private final String url;
	private final String appid;
	private final String pagepath;
	
	public MiniProgramButton(String name, String url, String appid, String pagepath) {
		super(ButtonType.miniprogram, name);
		this.url = url;
		this.appid = appid;
		this.pagepath = pagepath;
	}

	public String getUrl() {
		return url;
	}

	public String getAppid() {
		return appid;
	}

	public String getPagepath() {
		return pagepath;
	}
}
