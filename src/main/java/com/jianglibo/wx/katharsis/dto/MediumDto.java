package com.jianglibo.wx.katharsis.dto;

import com.jianglibo.wx.annotation.DtoToEntity;
import com.jianglibo.wx.config.JsonApiResourceNames;
import com.jianglibo.wx.domain.Medium;

import io.katharsis.resource.annotations.JsonApiResource;

@JsonApiResource(type = JsonApiResourceNames.MEDIUM)
@DtoToEntity(entityClass=Medium.class)
public class MediumDto extends DtoBase<MediumDto, Medium>{
	
	private String url;
	private String contentType;
	private String localPath;
	private long size;
	
	
	@Override
	public String toString() {
		return String.format("[%s,%s]", getId(), getUrl());
	}


	public String getUrl() {
		return url;
	}


	public void setUrl(String url) {
		this.url = url;
	}


	public String getContentType() {
		return contentType;
	}


	public void setContentType(String contentType) {
		this.contentType = contentType;
	}


	public String getLocalPath() {
		return localPath;
	}


	public void setLocalPath(String localPath) {
		this.localPath = localPath;
	}


	public long getSize() {
		return size;
	}


	public void setSize(long size) {
		this.size = size;
	}

}
