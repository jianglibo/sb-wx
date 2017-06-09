﻿package com.jianglibo.wx.katharsis.dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Pattern;

import com.jianglibo.wx.annotation.DtoToEntity;
import com.jianglibo.wx.config.JsonApiResourceNames;
import com.jianglibo.wx.domain.CrawlCat;

import io.katharsis.resource.annotations.JsonApiRelation;
import io.katharsis.resource.annotations.JsonApiResource;
import io.katharsis.resource.annotations.LookupIncludeBehavior;
import io.katharsis.resource.annotations.SerializeType;

@JsonApiResource(type = JsonApiResourceNames.CRAWL_CAT)
@DtoToEntity(entityClass=CrawlCat.class)
public class CrawlCatDto extends DtoBase<CrawlCatDto, CrawlCat>{
	
	@Pattern(regexp="[a-zA-Z]{1}[a-zA-Z0-9]+")
	private String name;
	
	private String description;
	
	@JsonApiRelation(lookUp=LookupIncludeBehavior.NONE,serialize=SerializeType.LAZY, opposite="crawlCat")
	private List<SiteDto> sites = new ArrayList<>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public CrawlCatDto fromEntity(CrawlCat entity) {
		setDescription(entity.getDescription());
		setId(entity.getId());
		setName(entity.getName());
		setCreatedAt(entity.getCreatedAt());
		return this;
	}

	@Override
	public CrawlCat patch(CrawlCat entity) {
		entity.setDescription(getDescription());
		entity.setName(getName());
		return entity;
	}

	public List<SiteDto> getSites() {
		return sites;
	}

	public void setSites(List<SiteDto> sites) {
		this.sites = sites;
	}
}
