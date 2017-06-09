package com.jianglibo.wx.message.in;

import com.jianglibo.wx.message.WxInMessage;

public class VoiceMessage extends WxInMessage {

	private String format;
	private String mediaId;
	
	private String recognition;
	
	public VoiceMessage() {
		super();
		setMsgType(WxMessageType.voice);
	}
	
	public String getMediaId() {
		return mediaId;
	}
	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}
	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}
	public String getRecognition() {
		return recognition;
	}
	public void setRecognition(String recognition) {
		this.recognition = recognition;
	}
	
	
}
