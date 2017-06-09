package com.jianglibo.wx.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlCData;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName = "item") 
public class WxNewsArticle {
	
	@JacksonXmlCData
	@JsonProperty(value="Title")
	private String title;

	@JacksonXmlCData
	@JsonProperty(value="Description")
	private String description;
	
	@JacksonXmlCData
	@JsonProperty(value="PicUrl")
	private String picUrl;

	@JacksonXmlCData
	@JsonProperty(value="Url")
	private String url;
	
	public WxNewsArticle(){}
	
	public WxNewsArticle(String title, String description, String picUrl, String url) {
		setTitle(title);
		setDescription(description);
		setPicUrl(picUrl);
		setUrl(url);
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
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
