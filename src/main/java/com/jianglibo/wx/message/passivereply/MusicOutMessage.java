package com.jianglibo.wx.message.passivereply;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlCData;
import com.jianglibo.wx.message.WxOutMessage;

public class MusicOutMessage extends WxOutMessage {
	
	@JsonProperty(value="Music")
	private Music music;
	
	public MusicOutMessage() {
		super();
		setMsgType(WxMessageType.music);
	}

	public Music getMusic() {
		return music;
	}

	public void setMusic(Music music) {
		this.music = music;
	}



	public static class Music {
		
		@JacksonXmlCData
		@JsonProperty(value="Title")
		private String title;

		@JacksonXmlCData
		@JsonProperty(value="Description")
		private String description;
		
		@JacksonXmlCData
		@JsonProperty(value="MusicUrl")
		private String musicUrl;
		
		@JacksonXmlCData
		@JsonProperty(value="HQMusicUrl")
		private String hQMusicUrl;

		@JacksonXmlCData
		@JsonProperty(value="ThumbMediaId")
		private String thumbMediaId;
		
		public Music(){}

		public String getMusicUrl() {
			return musicUrl;
		}

		public void setMusicUrl(String musicUrl) {
			this.musicUrl = musicUrl;
		}

		public String gethQMusicUrl() {
			return hQMusicUrl;
		}

		public void sethQMusicUrl(String hQMusicUrl) {
			this.hQMusicUrl = hQMusicUrl;
		}

		public String getThumbMediaId() {
			return thumbMediaId;
		}

		public void setThumbMediaId(String thumbMediaId) {
			this.thumbMediaId = thumbMediaId;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}
	}
	
	public static class MusicOutMessageBuilder extends WxOutMessageBuilder<MusicOutMessage> {
		
		private Music music;

		public MusicOutMessageBuilder(String toUserName, String fromUserName) {
			super(toUserName, fromUserName, WxMessageType.music);
			music = new Music();
		}
		
		public MusicOutMessageBuilder withTitleAndDescription(String title, String description) {
			music.setTitle(title);
			music.setDescription(description);
			return this;
		}
		
		public MusicOutMessageBuilder withMusicUrl(String musicUrl) {
			music.setMusicUrl(musicUrl);
			return this;
		}
		
		public MusicOutMessageBuilder withThumbMediaId(String thumbMediaId) {
			music.setThumbMediaId(thumbMediaId);
			return this;
		}
		
		public MusicOutMessageBuilder withHQMusicUrl(String hQMusicUrl) {
			music.sethQMusicUrl(hQMusicUrl);
			return this;
		}

		@Override
		public MusicOutMessage build() {
			MusicOutMessage imm = new MusicOutMessage();
			imm.setMusic(music);
			setRequiredFields(imm);
			return imm;
		}
	}
}
