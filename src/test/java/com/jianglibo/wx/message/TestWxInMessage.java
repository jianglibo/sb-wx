package com.jianglibo.wx.message;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Test;

import com.jianglibo.wx.WxBase;
import com.jianglibo.wx.message.WxBody.WxMessageType;
import com.jianglibo.wx.message.in.ImageMessage;
import com.jianglibo.wx.message.in.LinkMessage;
import com.jianglibo.wx.message.in.LocationMessage;
import com.jianglibo.wx.message.in.ShortVedioMessage;
import com.jianglibo.wx.message.in.TextMessage;
import com.jianglibo.wx.message.in.VideoMessage;
import com.jianglibo.wx.message.in.VoiceMessage;

public class TestWxInMessage extends WxBase {
	
	@Test
	public void tTextMessage() throws IOException {
		String rm = getFixtureWithExplicitName("textmessage.xml");
		TextMessage rmdto = (TextMessage) wxUtil.deserialize(rm);
		assertThat(rmdto.getContent(), equalTo("this is a test"));
		assertThat(rmdto.getCreateTime(), equalTo(1348831860L));
		assertThat(rmdto.getFromUserName(), equalTo("fromUser"));
		assertThat(rmdto.getMsgId(), equalTo(1234567890123456L));
		assertThat(rmdto.getMsgType(), equalTo(WxMessageType.text));
		assertThat(rmdto.getToUserName(), equalTo("toUser"));
		String s = wxUtil.serialize(rmdto);
		assertTrue("serialized content should contains <![CDATA[", s.contains("<![CDATA["));
	}
	
	@Test
	public void tImageMessage() throws IOException {
		String rm = getFixtureWithExplicitName("imagemessage.xml");
		ImageMessage rmdto = (ImageMessage) wxUtil.deserialize(rm);
		assertThat(rmdto.getPicUrl(), equalTo("this is a url"));
		assertThat(rmdto.getMediaId(), equalTo("media_id"));
		assertThat(rmdto.getCreateTime(), equalTo(1348831860L));
		assertThat(rmdto.getFromUserName(), equalTo("fromUser"));
		assertThat(rmdto.getMsgId(), equalTo(1234567890123456L));
		assertThat(rmdto.getMsgType(), equalTo(WxMessageType.image));
		assertThat(rmdto.getToUserName(), equalTo("toUser"));
	}
	
	@Test
	public void tVoiceMessage() throws IOException {
		String rm = getFixtureWithExplicitName("voicemessage.xml");
		VoiceMessage rmdto = (VoiceMessage) wxUtil.deserialize(rm);
		assertThat(rmdto.getFormat(), equalTo("Format"));
		assertThat(rmdto.getMediaId(), equalTo("media_id"));
		assertThat(rmdto.getCreateTime(), equalTo(1357290913L));
		assertThat(rmdto.getFromUserName(), equalTo("fromUser"));
		assertThat(rmdto.getMsgId(), equalTo(1234567890123456L));
		assertThat(rmdto.getMsgType(), equalTo(WxMessageType.voice));
		assertThat(rmdto.getRecognition(), equalTo("腾讯微信团队"));
		assertThat(rmdto.getToUserName(), equalTo("toUser"));
	}
	
	@Test
	public void tVedioMessage() throws IOException {
		String rm = getFixtureWithExplicitName("videomessage.xml");
		VideoMessage rmdto = (VideoMessage) wxUtil.deserialize(rm);
		assertThat(rmdto.getMediaId(), equalTo("media_id"));
		assertThat(rmdto.getCreateTime(), equalTo(1357290913L));
		assertThat(rmdto.getFromUserName(), equalTo("fromUser"));
		assertThat(rmdto.getMsgId(), equalTo(1234567890123456L));
		assertThat(rmdto.getMsgType(), equalTo(WxMessageType.video));
		assertThat(rmdto.getThumbMediaId(), equalTo("thumb_media_id"));
		assertThat(rmdto.getToUserName(), equalTo("toUser"));
	}
	
	@Test
	public void tShortVedioMessage() throws IOException {
		String rm = getFixtureWithExplicitName("shortvideomessage.xml");
		ShortVedioMessage rmdto = (ShortVedioMessage) wxUtil.deserialize(rm);
		assertThat(rmdto.getMediaId(), equalTo("media_id"));
		assertThat(rmdto.getCreateTime(), equalTo(1357290913L));
		assertThat(rmdto.getFromUserName(), equalTo("fromUser"));
		assertThat(rmdto.getMsgId(), equalTo(1234567890123456L));
		assertThat(rmdto.getMsgType(), equalTo(WxMessageType.shortvideo));
		assertThat(rmdto.getThumbMediaId(), equalTo("thumb_media_id"));
		assertThat(rmdto.getToUserName(), equalTo("toUser"));
	}
	
	@Test
	public void tLocationMessage() throws IOException {
		String rm = getFixtureWithExplicitName("locationmessage.xml");
		LocationMessage rmdto = (LocationMessage) wxUtil.deserialize(rm);
		assertThat(rmdto.getLocation_X(), equalTo(23.134521));
		assertThat(rmdto.getLocation_Y(), equalTo(113.358803));
		assertThat(rmdto.getCreateTime(), equalTo(1351776360L));
		assertThat(rmdto.getFromUserName(), equalTo("fromUser"));
		assertThat(rmdto.getMsgId(), equalTo(1234567890123456L));
		assertThat(rmdto.getMsgType(), equalTo(WxMessageType.location));
		assertThat(rmdto.getScale(), equalTo(20));
		assertThat(rmdto.getLabel(), equalTo("位置信息"));
		assertThat(rmdto.getToUserName(), equalTo("toUser"));
	}
	
	@Test
	public void tLinkMessage() throws IOException {
		String rm = getFixtureWithExplicitName("linkmessage.xml");
		LinkMessage rmdto = (LinkMessage) wxUtil.deserialize(rm);
		assertThat(rmdto.getDescription(), equalTo("公众平台官网链接"));
		assertThat(rmdto.getUrl(), equalTo("url"));
		assertThat(rmdto.getCreateTime(), equalTo(1351776360L));
		assertThat(rmdto.getFromUserName(), equalTo("fromUser"));
		assertThat(rmdto.getMsgId(), equalTo(1234567890123456L));
		assertThat(rmdto.getMsgType(), equalTo(WxMessageType.link));
		assertThat(rmdto.getTitle(), equalTo("公众平台官网链接"));
		assertThat(rmdto.getToUserName(), equalTo("toUser"));
	}

}
