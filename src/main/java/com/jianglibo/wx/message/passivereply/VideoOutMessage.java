package com.jianglibo.wx.message.passivereply;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlCData;
import com.jianglibo.wx.message.WxOutMessage;

public class VideoOutMessage extends WxOutMessage {
	
	@JsonProperty(value="Video")
	private Video video;
	
	public VideoOutMessage() {
		super();
		setMsgType(WxMessageType.video);
	}

	public Video getVideo() {
		return video;
	}

	public void setVideo(Video video) {
		this.video = video;
	}

	public static class Video {
		
		@JacksonXmlCData
		@JsonProperty(value="MediaId")
		private String mediaId;
		
		@JacksonXmlCData
		@JsonProperty(value="Title")
		private String title;

		@JacksonXmlCData
		@JsonProperty(value="Description")
		private String description;
		
		public Video(){}
		
		public String getMediaId() {
			return mediaId;
		}
		public void setMediaId(String mediaId) {
			this.mediaId = mediaId;
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
	
	public static class VideoOutMessageBuilder extends WxOutMessageBuilder<VideoOutMessage> {
		
		private Video video;

		public VideoOutMessageBuilder(String toUserName, String fromUserName, String videoMediaId) {
			super(toUserName, fromUserName, WxMessageType.video);
			video = new Video();
			video.setMediaId(videoMediaId);
		}
		
		public VideoOutMessageBuilder withVideoTitle(String title) {
			video.setTitle(title);
			return this;
		}
		
		public VideoOutMessageBuilder withVideoDescription(String description) {
			video.setDescription(description);
			return this;
		}

		@Override
		public VideoOutMessage build() {
			VideoOutMessage imm = new VideoOutMessage();
			imm.setVideo(video);
			setRequiredFields(imm);
			return imm;
		}
		
	}
}
