package com.jianglibo.wx.message;

import static org.junit.Assert.assertTrue;

import java.time.Instant;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jianglibo.wx.WxBase;
import com.jianglibo.wx.message.passivereply.ImageOutMessage;
import com.jianglibo.wx.message.passivereply.MusicOutMessage;
import com.jianglibo.wx.message.passivereply.NewsOutMessage;
import com.jianglibo.wx.message.passivereply.TextOutMessage;
import com.jianglibo.wx.message.passivereply.VideoOutMessage;
import com.jianglibo.wx.message.passivereply.VoiceOutMessage;
import com.jianglibo.wx.message.passivereply.TextOutMessage.TextOutMessageBuilder;

public class TestWxOutMessage extends WxBase {
	
	@Test
	public void tTextOutMessage() throws JsonProcessingException {
		TextOutMessage tom = new TextOutMessageBuilder("touser", "fromuser").withContent("你好").withCreateTime(Instant.now().toEpochMilli()).build();
		String s = wxUtil.serialize(tom);
		assertTrue("content should cdata-lized.", s.matches(".*<Content><!\\[CDATA\\[你好\\]\\]>.*"));
		assertTrue("createTime shouldn't cdata-lized.", s.matches(".*<CreateTime>\\d+</CreateTime>.*"));
		assertTrue(s.matches(".*<ToUserName><!\\[CDATA\\[touser\\]\\]></ToUserName>.*"));
		assertTrue(s.matches(".*<FromUserName><!\\[CDATA\\[fromuser\\]\\]></FromUserName>.*"));
		assertTrue(s.matches(".*<MsgType><!\\[CDATA\\[text\\]\\]></MsgType>.*"));
		printme(s);
	}
	
	@Test
	public void tImageOutMessage() throws JsonProcessingException {
		ImageOutMessage tom = new ImageOutMessage.ImageOutMessageBuilder("touser", "fromuser").withImageMediaId("media_id").build();
		String s = wxUtil.serialize(tom);
		assertTrue(s.matches(".*<MsgType><!\\[CDATA\\[image\\]\\]></MsgType>.*"));
		assertTrue(s.matches(".*<Image><MediaId><!\\[CDATA\\[media_id\\]\\]></MediaId></Image>.*"));
		assertTrue("createTime shouldn't cdata-lized.", s.matches(".*<CreateTime>\\d+</CreateTime>.*"));
	}

	@Test
	public void tVoiceOutMessage() throws JsonProcessingException {
		VoiceOutMessage tom = new VoiceOutMessage.VoiceOutMessageBuilder("touser", "fromuser").withVoiceMediaId("media_id").build();
		String s = wxUtil.serialize(tom);
		assertTrue(s.matches(".*<MsgType><!\\[CDATA\\[voice\\]\\]></MsgType>.*"));
		assertTrue(s.matches(".*<Voice><MediaId><!\\[CDATA\\[media_id\\]\\]></MediaId></Voice>.*"));
		assertTrue("createTime shouldn't cdata-lized.", s.matches(".*<CreateTime>\\d+</CreateTime>.*"));
	}
	
	@Test
	public void tVideoOutMessage() throws JsonProcessingException {
		VideoOutMessage tom = new VideoOutMessage.VideoOutMessageBuilder("touser", "fromuser", "media_id").withVideoDescription("description").withVideoTitle("title").build();
		String s = wxUtil.serialize(tom);
		assertTrue(s.matches(".*<MsgType><!\\[CDATA\\[video\\]\\]></MsgType>.*"));
		assertTrue(s.matches(".*<Video><MediaId><!\\[CDATA\\[media_id\\]\\]></MediaId><Title><!\\[CDATA\\[title\\]]></Title><Description><!\\[CDATA\\[description\\]\\]></Description></Video>.*"));
		assertTrue("createTime shouldn't cdata-lized.", s.matches(".*<CreateTime>\\d+</CreateTime>.*"));
	}
	
	@Test
	public void tMusicOutMessage() throws JsonProcessingException {
		MusicOutMessage tom = new MusicOutMessage.MusicOutMessageBuilder("touser", "fromuser")
				.withTitleAndDescription("TITLE", "DESCRIPTION")
				.withHQMusicUrl("HQ_MUSIC_Url")
				.withMusicUrl("MUSIC_Url")
				.withThumbMediaId("media_id")
				.build();
		String s = wxUtil.serialize(tom);
		assertTrue(s.matches(".*<MsgType><!\\[CDATA\\[music\\]\\]></MsgType>.*"));
		assertTrue(s.matches(".*<Music><Title><!\\[CDATA\\[TITLE\\]\\]></Title><Description><!\\[CDATA\\[DESCRIPTION\\]\\]></Description><MusicUrl><!\\[CDATA\\[MUSIC_Url\\]\\]></MusicUrl><HQMusicUrl><!\\[CDATA\\[HQ_MUSIC_Url\\]\\]></HQMusicUrl><ThumbMediaId><!\\[CDATA\\[media_id\\]\\]></ThumbMediaId></Music>.*"));
		assertTrue("createTime shouldn't cdata-lized.", s.matches(".*<CreateTime>\\d+</CreateTime>.*"));
	}
	
	@Test
	public void tNewsOutMessage() throws JsonProcessingException {
		NewsOutMessage tom = new NewsOutMessage.NewsOutMessageBuilder("touser", "fromuser")
				.addArticle("title1", "description1", "picurl", "url")
				.addArticle("title", "description", "picurl", "url")
				.build();
		String s = wxUtil.serialize(tom);
		assertTrue(s.matches(".*<MsgType><!\\[CDATA\\[news\\]\\]></MsgType>.*"));
		assertTrue(s.matches(".*<ArticleCount>2</ArticleCount>.*"));
		assertTrue(s.matches(".*<PicUrl><!\\[CDATA\\[picurl\\]\\]></PicUrl>.*"));
		assertTrue(s.matches(".*<Url><!\\[CDATA\\[url\\]\\]></Url>.*"));
		assertTrue(s.matches(".*<Title><!\\[CDATA\\[title1\\]\\]></Title>.*"));
		assertTrue(s.matches(".*<Articles><item><Title><!\\[CDATA\\[title1\\]\\]></Title>.*"));
		assertTrue(s.matches(".*<Description><!\\[CDATA\\[description1\\]\\]></Description>.*"));
		assertTrue("createTime shouldn't cdata-lized.", s.matches(".*<CreateTime>\\d+</CreateTime>.*"));
	}

}
