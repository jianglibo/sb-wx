package com.jianglibo.wx.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Proxy;

@Entity
@Table(name = "post")
@Proxy(lazy=false)
public class Post extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
    @NotNull
    @Column(nullable = false)
	private String title;
	
	private String content;
	
	@ManyToOne
	private BootUser creator;
	
	@OneToMany(mappedBy="post", fetch=FetchType.EAGER)
	private List<Medium> media = new ArrayList<>();
	
	@OneToMany(mappedBy="post", fetch=FetchType.LAZY, cascade=CascadeType.REMOVE)
	private List<PostShare> postShares = new ArrayList<>();

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public List<Medium> getMedia() {
		return media;
	}

	public void setMedia(List<Medium> media) {
		this.media = media;
	}

	public BootUser getCreator() {
		return creator;
	}

	public void setCreator(BootUser creator) {
		this.creator = creator;
	}

	public List<PostShare> getPostShares() {
		return postShares;
	}

	public void setPostShares(List<PostShare> postShares) {
		this.postShares = postShares;
	}

	@Override
	public String[] propertiesOnCreating() {
		// TODO Auto-generated method stub
		return null;
	}
}
