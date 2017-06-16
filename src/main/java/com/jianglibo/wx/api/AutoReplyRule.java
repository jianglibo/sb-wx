package com.jianglibo.wx.api;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class AutoReplyRule {

	private String url = "https://api.weixin.qq.com/cgi-bin/get_current_autoreply_info?access_token=";
	
	private RestTemplate restTemplate = new RestTemplate();
	
	@Autowired
	private AccessTokenHolder tokenHolder;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	public AutoReplySetting getAutoReplySetting() throws JsonParseException, JsonMappingException, IOException {
		return  objectMapper.readValue(getAutoReplySettingString(), AutoReplySetting.class);
	}
	
	protected String getAutoReplySettingString() {
		ResponseEntity<String> resp = restTemplate.getForEntity(url + tokenHolder.getAccessToken(), String.class);
		return resp.getBody();
	}
	
	public static class AutoReplySetting {
		private int is_add_friend_reply_open;
		private int is_autoreply_open;
		
		private TypeContent add_friend_autoreply_info;
		private TypeContent message_default_autoreply_info;
		
		private KeywordAutoreplyInfoWrapper keyword_autoreply_info;

		public int getIs_add_friend_reply_open() {
			return is_add_friend_reply_open;
		}

		public void setIs_add_friend_reply_open(int is_add_friend_reply_open) {
			this.is_add_friend_reply_open = is_add_friend_reply_open;
		}

		public int getIs_autoreply_open() {
			return is_autoreply_open;
		}

		public void setIs_autoreply_open(int is_autoreply_open) {
			this.is_autoreply_open = is_autoreply_open;
		}

		public TypeContent getAdd_friend_autoreply_info() {
			return add_friend_autoreply_info;
		}

		public void setAdd_friend_autoreply_info(TypeContent add_friend_autoreply_info) {
			this.add_friend_autoreply_info = add_friend_autoreply_info;
		}

		public TypeContent getMessage_default_autoreply_info() {
			return message_default_autoreply_info;
		}

		public void setMessage_default_autoreply_info(TypeContent message_default_autoreply_info) {
			this.message_default_autoreply_info = message_default_autoreply_info;
		}

		public KeywordAutoreplyInfoWrapper getKeyword_autoreply_info() {
			return keyword_autoreply_info;
		}

		public void setKeyword_autoreply_info(KeywordAutoreplyInfoWrapper keyword_autoreply_info) {
			this.keyword_autoreply_info = keyword_autoreply_info;
		}
	}
	
	
	public static class KeywordAutoreplyInfoWrapper {
		private List<KeywordAutoreplyInfo> list;

		public List<KeywordAutoreplyInfo> getList() {
			return list;
		}

		public void setList(List<KeywordAutoreplyInfo> list) {
			this.list = list;
		}
	}
	
	public static class KeywordAutoreplyInfo {
		private String rule_name;
		private long createTime;
		private String reply_mode;
		
		private List<KeywordInfo> keyword_list_info;
		private List<ReplyListInfo> reply_list_info;
		public String getRule_name() {
			return rule_name;
		}
		public void setRule_name(String rule_name) {
			this.rule_name = rule_name;
		}
		public long getCreateTime() {
			return createTime;
		}
		public void setCreateTime(long createTime) {
			this.createTime = createTime;
		}
		public String getReply_mode() {
			return reply_mode;
		}
		public void setReply_mode(String reply_mode) {
			this.reply_mode = reply_mode;
		}
		public List<KeywordInfo> getKeyword_list_info() {
			return keyword_list_info;
		}
		public void setKeyword_list_info(List<KeywordInfo> keyword_list_info) {
			this.keyword_list_info = keyword_list_info;
		}
		public List<ReplyListInfo> getReply_list_info() {
			return reply_list_info;
		}
		public void setReply_list_info(List<ReplyListInfo> reply_list_info) {
			this.reply_list_info = reply_list_info;
		}
	}
	
	public static class ReplyListInfo {
		private String type;
		
		private String content;
		
		private NewsInfoWrapper news_info; // exists only when type equals "news"
		
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public NewsInfoWrapper getNews_info() {
			return news_info;
		}
		public void setNews_info(NewsInfoWrapper news_info) {
			this.news_info = news_info;
		}
		public String getContent() {
			return content;
		}
		public void setContent(String content) {
			this.content = content;
		}
	}
	
	public static class NewsInfoWrapper {
		private List<NewsInfo> list;

		public List<NewsInfo> getList() {
			return list;
		}

		public void setList(List<NewsInfo> list) {
			this.list = list;
		}
	}
	
	public static class NewsInfo {
		private String title;
		private String author;
		private String digest;
		private String show_cover;
		private String conver_url;
		private String content_url;
		private String source_url;
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public String getAuthor() {
			return author;
		}
		public void setAuthor(String author) {
			this.author = author;
		}
		public String getDigest() {
			return digest;
		}
		public void setDigest(String digest) {
			this.digest = digest;
		}
		public String getShow_cover() {
			return show_cover;
		}
		public void setShow_cover(String show_cover) {
			this.show_cover = show_cover;
		}
		public String getConver_url() {
			return conver_url;
		}
		public void setConver_url(String conver_url) {
			this.conver_url = conver_url;
		}
		public String getContent_url() {
			return content_url;
		}
		public void setContent_url(String content_url) {
			this.content_url = content_url;
		}
		public String getSource_url() {
			return source_url;
		}
		public void setSource_url(String source_url) {
			this.source_url = source_url;
		}
	}
	
	public static class KeywordInfo {
		private String type;
		private String match_mode;
		private String content; // this is keyword itself.
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public String getMatch_mode() {
			return match_mode;
		}
		public void setMatch_mode(String match_mode) {
			this.match_mode = match_mode;
		}
		public String getContent() {
			return content;
		}
		public void setContent(String content) {
			this.content = content;
		}
	}
	
	
	public static class TypeContent {
		private String type; //text, img, voice,video
		private String content; // for text type, it's content, for others, it's mediaId.
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public String getContent() {
			return content;
		}
		public void setContent(String content) {
			this.content = content;
		}
	}
}
