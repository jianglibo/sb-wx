package com.jianglibo.wx.katharsis.dto;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.jianglibo.wx.annotation.DtoToEntity;
import com.jianglibo.wx.config.JsonApiResourceNames;
import com.jianglibo.wx.domain.Site;
import com.jianglibo.wx.domain.Site.SiteProtocol;

import io.katharsis.resource.annotations.JsonApiRelation;
import io.katharsis.resource.annotations.JsonApiResource;
import io.katharsis.resource.annotations.LookupIncludeBehavior;
import io.katharsis.resource.annotations.SerializeType;

@JsonApiResource(type = JsonApiResourceNames.SITE)
@DtoToEntity(entityClass=Site.class)
public class SiteDto extends DtoBase<SiteDto, Site> {

	private SiteProtocol protocol = SiteProtocol.HTTP;
	
	@NotBlank
	private String domainName;
	
	private String entryPath;
	
	@NotNull
	@JsonApiRelation(lookUp=LookupIncludeBehavior.AUTOMATICALLY_WHEN_NULL,serialize=SerializeType.LAZY, opposite="sites")
	private CrawlCatDto crawlCat;
	
	@Override
	public SiteDto fromEntity(Site entity) {
		setCreatedAt(entity.getCreatedAt());
		setDomainName(entity.getDomainName());
		setEntryPath(entity.getEntryPath());
		setProtocol(entity.getProtocol());
		setId(entity.getId());
		return this;
	}

	@Override
	public Site patch(Site entity) {
		entity.setDomainName(getDomainName());
		entity.setEntryPath(getEntryPath());
		entity.setProtocol(getProtocol());
		return entity;
	}

	public CrawlCatDto getCrawlCat() {
		return crawlCat;
	}

	public void setCrawlCat(CrawlCatDto crawlCat) {
		this.crawlCat = crawlCat;
	}

	public SiteProtocol getProtocol() {
		return protocol;
	}

	public void setProtocol(SiteProtocol protocol) {
		this.protocol = protocol;
	}

	public String getDomainName() {
		return domainName;
	}

	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}

	public String getEntryPath() {
		return entryPath;
	}

	public void setEntryPath(String entryPath) {
		this.entryPath = entryPath;
	}
}
