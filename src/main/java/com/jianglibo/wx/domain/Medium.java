package com.jianglibo.wx.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Proxy;

@Entity
@Table(name = "media")
@Proxy(lazy=false)
public class Medium extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String orignName;
	
	@ManyToOne
	private BootUser creator;
	
	private String url;
	private String contentType;
	private String localPath;
	private long size;
	
	@ManyToOne
	private Post post;
	
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
	public Post getPost() {
		return post;
	}
	public void setPost(Post post) {
		this.post = post;
	}
	public String getOrignName() {
		return orignName;
	}
	public void setOrignName(String orignName) {
		this.orignName = orignName;
	}
	public BootUser getCreator() {
		return creator;
	}
	public void setCreator(BootUser creator) {
		this.creator = creator;
	}
	@Override
	public String[] propertiesOnCreating() {
		// TODO Auto-generated method stub
		return null;
	}
}
