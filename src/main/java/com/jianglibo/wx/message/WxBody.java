package com.jianglibo.wx.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlCData;

@JsonRootName(value="xml")
public abstract class WxBody {
	
	@JacksonXmlCData
	@JsonProperty("ToUserName")
	private String toUserName;
	@JacksonXmlCData
	@JsonProperty("FromUserName")
	private String fromUserName;
	@JsonProperty("CreateTime")
	private long createTime;
	@JacksonXmlCData
	@JsonProperty("MsgType")
	private WxMessageType msgType;
	
	public static enum WxMessageType {
		text, image, voice, video, shortvideo, location, link, event, music, news;
	}

	public String getToUserName() {
		return toUserName;
	}

	public void setToUserName(String toUserName) {
		this.toUserName = toUserName;
	}

	public String getFromUserName() {
		return fromUserName;
	}

	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public WxMessageType getMsgType() {
		return msgType;
	}

	public void setMsgType(WxMessageType msgType) {
		this.msgType = msgType;
	}
	
	public static abstract class WxBodyBuilder<T extends WxBody> {
		private final String toUserName;
		private final String fromUserName;
		private final WxMessageType msgType;
		
		public WxBodyBuilder(String toUserName, String fromUserName, WxMessageType msgType) {
			super();
			this.toUserName = toUserName;
			this.fromUserName = fromUserName;
			this.msgType = msgType;
		}
		
		protected void setRequiredFields(T msg) {
			msg.setFromUserName(fromUserName);
			msg.setToUserName(toUserName);
			msg.setMsgType(msgType);
		}
		
		public abstract T build();
	}
}
