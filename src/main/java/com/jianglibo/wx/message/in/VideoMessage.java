package com.jianglibo.wx.message.in;

import com.jianglibo.wx.message.WxInMessage;

public class VideoMessage extends WxInMessage {

	private String mediaId;
	private String thumbMediaId;
	
	public VideoMessage() {
		super();
		setMsgType(WxMessageType.video);
	}
	
	public String getThumbMediaId() {
		return thumbMediaId;
	}
	public void setThumbMediaId(String thumbMediaId) {
		this.thumbMediaId = thumbMediaId;
	}
	
	public String getMediaId() {
		return mediaId;
	}
	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}

}
