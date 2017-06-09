package com.jianglibo.wx.message.passivereply;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.jianglibo.wx.message.WxNewsArticle;
import com.jianglibo.wx.message.WxOutMessage;

public class NewsOutMessage extends WxOutMessage {
	
	@JsonProperty(value="Articles")
	@JsonSerialize(using = WxNewsArticleListSerializer.class)
    @JacksonXmlElementWrapper(useWrapping = false)
	private List<WxNewsArticle> articles;
	
	@JsonProperty(value="ArticleCount")
	private int articleCount;
	
	public NewsOutMessage() {
		super();
		setMsgType(WxMessageType.news);
		articles = new ArrayList<WxNewsArticle>();
	}

	public List<WxNewsArticle> getArticles() {
		return articles;
	}

	public void setArticles(List<WxNewsArticle> articles) {
		this.articles = articles;
	}

	public int getArticleCount() {
		return articleCount;
	}

	public void setArticleCount(int articleCount) {
		this.articleCount = articleCount;
	}
	
	public static class WxNewsArticleListSerializer extends JsonSerializer<List<WxNewsArticle>> {

	    @Override
	    public void serialize(List<WxNewsArticle> value, JsonGenerator jgen,
	            SerializerProvider provider) throws IOException,
	            JsonProcessingException {
	        jgen.writeStartArray();
	        for (WxNewsArticle model : value) {
	            jgen.writeStartObject();
	            jgen.writeObjectField("item", model);
	            jgen.writeEndObject();    
	        }
	        jgen.writeEndArray();
	    }

	}
	
	public static class NewsOutMessageBuilder extends WxOutMessageBuilder<NewsOutMessage> {
		
		private List<WxNewsArticle> articles;

		public NewsOutMessageBuilder(String toUserName, String fromUserName) {
			super(toUserName, fromUserName, WxMessageType.news);
			articles = new ArrayList<>();
		}
		
		public NewsOutMessageBuilder addArticle(String title, String description, String picUrl, String url) {
			articles.add(new WxNewsArticle(title, description, picUrl, url));
			return this;
		}
		
		public NewsOutMessageBuilder addArticle(WxNewsArticle article) {
			articles.add(article);
			return this;
		}
		
		public NewsOutMessageBuilder addArticle(Iterable<WxNewsArticle> articleIt) {
			Iterator<WxNewsArticle> it = articleIt.iterator();
			while(it.hasNext()) {
				articles.add(it.next());
			}
			return this;
		}

		@Override
		public NewsOutMessage build() {
			NewsOutMessage imm = new NewsOutMessage();
			imm.setArticles(articles);
			imm.setArticleCount(articles.size());
			setRequiredFields(imm);
			return imm;
		}
		
	}
}
