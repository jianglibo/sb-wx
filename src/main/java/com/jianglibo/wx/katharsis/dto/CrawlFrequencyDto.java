package com.jianglibo.wx.katharsis.dto;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

import com.jianglibo.wx.annotation.DtoToEntity;
import com.jianglibo.wx.config.JsonApiResourceNames;
import com.jianglibo.wx.domain.CrawlFrequency;

import io.katharsis.resource.annotations.JsonApiRelation;
import io.katharsis.resource.annotations.JsonApiResource;
import io.katharsis.resource.annotations.LookupIncludeBehavior;
import io.katharsis.resource.annotations.SerializeType;

@JsonApiResource(type = JsonApiResourceNames.CRAWL_FRE)
@DtoToEntity(entityClass=CrawlFrequency.class)
public class CrawlFrequencyDto extends DtoBase<CrawlFrequencyDto, CrawlFrequency> {

	@NotBlank
	private String regex;
	
	@Range(min=300000)
	private int seconds;
	
	@NotNull
	@JsonApiRelation(lookUp=LookupIncludeBehavior.AUTOMATICALLY_ALWAYS,serialize=SerializeType.EAGER, opposite="crawlFrenquencies")
	private SiteDto site;
	
	public String getRegex() {
		return regex;
	}

	public void setRegex(String regex) {
		this.regex = regex;
	}

	public int getSeconds() {
		return seconds;
	}

	public void setSeconds(int seconds) {
		this.seconds = seconds;
	}

	public SiteDto getSite() {
		return site;
	}

	public void setSite(SiteDto site) {
		this.site = site;
	}

	@Override
	public CrawlFrequencyDto fromEntity(CrawlFrequency entity) {
		setId(entity.getId());
		setRegex(entity.getRegex());
		setSeconds(entity.getSeconds());
		setSite(new SiteDto().fromEntity(entity.getSite()));
		setCreatedAt(entity.getCreatedAt());
		return this;
	}

	@Override
	public CrawlFrequency patch(CrawlFrequency entity) {
		entity.setRegex(getRegex());
		entity.setSeconds(getSeconds());
		return entity;
	}

}
