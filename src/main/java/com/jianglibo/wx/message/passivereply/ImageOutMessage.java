package com.jianglibo.wx.message.passivereply;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlCData;
import com.jianglibo.wx.message.WxOutMessage;

public class ImageOutMessage extends WxOutMessage {

	@JsonProperty(value = "Image")
	private Image image;

	public ImageOutMessage() {
		super();
		setMsgType(WxMessageType.image);
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public static class Image {

		@JacksonXmlCData
		@JsonProperty(value = "MediaId")
		private String mediaId;

		public Image() {
		}

		public String getMediaId() {
			return mediaId;
		}

		public void setMediaId(String mediaId) {
			this.mediaId = mediaId;
		}
	}

	public static class ImageOutMessageBuilder extends WxOutMessageBuilder<ImageOutMessage> {

		private Image image = new Image();

		public ImageOutMessageBuilder(String toUserName, String fromUserName) {
			super(toUserName, fromUserName, WxMessageType.image);
		}

		public ImageOutMessageBuilder withImageMediaId(String mediaId) {
			image.setMediaId(mediaId);
			return this;
		}

		@Override
		public ImageOutMessage build() {
			ImageOutMessage imm = new ImageOutMessage();
			imm.setImage(image);
			setRequiredFields(imm);
			return imm;
		}

	}

}
