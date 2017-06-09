package com.jianglibo.wx.message.passivereply;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlCData;
import com.jianglibo.wx.message.WxOutMessage;

public class TextOutMessage extends WxOutMessage {

	@JsonProperty(value="Content")
	@JacksonXmlCData
	private String content;
	
	public TextOutMessage() {
		super();
		setMsgType(WxMessageType.text);
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	public static class TextOutMessageBuilder extends WxOutMessageBuilder<TextOutMessage> {
		
		private String content;
		private long createTime;
		
		public TextOutMessageBuilder(String toUserName, String fromUserName) {
			super(toUserName, fromUserName, WxMessageType.text);
		}
		
		public TextOutMessageBuilder withContent(String content) {
			this.content = content;
			return this;
		}
		
		public TextOutMessageBuilder withCreateTime(long createTime) {
			this.createTime = createTime;
			return this;
		}

		@Override
		public TextOutMessage build() {
			TextOutMessage to = new TextOutMessage();
			to.setContent(content);
			to.setCreateTime(createTime);
			setRequiredFields(to);
			return to;
		}
		
	}
}
