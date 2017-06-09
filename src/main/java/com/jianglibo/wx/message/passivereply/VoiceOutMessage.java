package com.jianglibo.wx.message.passivereply;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlCData;
import com.jianglibo.wx.message.WxOutMessage;

public class VoiceOutMessage extends WxOutMessage {
	
	@JsonProperty(value="Voice")
	private Voice voice;
	
	public VoiceOutMessage() {
		super();
		setMsgType(WxMessageType.voice);
	}
	
	public Voice getVoice() {
		return voice;
	}

	public void setVoice(Voice voice) {
		this.voice = voice;
	}



	public static class Voice {
		
		@JacksonXmlCData
		@JsonProperty(value="MediaId")
		private String mediaId;
		
		public Voice(){}
		
		public String getMediaId() {
			return mediaId;
		}
		public void setMediaId(String mediaId) {
			this.mediaId = mediaId;
		}
	}
	
	public static class VoiceOutMessageBuilder extends WxOutMessageBuilder<VoiceOutMessage> {
		
		private Voice voice = new Voice(); 

		public VoiceOutMessageBuilder(String toUserName, String fromUserName) {
			super(toUserName, fromUserName, WxMessageType.voice);
		}
		
		public VoiceOutMessageBuilder withVoiceMediaId(String mediaId) {
			voice.setMediaId(mediaId);
			return this;
		}

		@Override
		public VoiceOutMessage build() {
			VoiceOutMessage imm = new VoiceOutMessage();
			imm.setVoice(voice);
			setRequiredFields(imm);
			return imm;
		}
		
	}
}
