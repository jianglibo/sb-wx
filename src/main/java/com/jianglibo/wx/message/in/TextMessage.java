package com.jianglibo.wx.message.in;

import com.jianglibo.wx.message.WxInMessage;

public class TextMessage extends WxInMessage {

	private String content;
	
	public TextMessage() {
		super();
		setMsgType(WxMessageType.text);
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
