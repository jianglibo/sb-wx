package com.jianglibo.wx.katharsis.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.jianglibo.wx.domain.BaseEntity;

import io.katharsis.resource.annotations.JsonApiId;

public abstract class DtoBase<T, E extends BaseEntity> implements Dto<T, E> {

	@JsonApiId
	private Long id;
	
	private Date createdAt;
	
	@JsonProperty(access=Access.WRITE_ONLY)
	private String dtoApplyTo;
	
	@JsonProperty(access=Access.WRITE_ONLY)
	private String dtoAction;
	
	public DtoBase(){}
	
	public DtoBase(Long id) {
		this.id = id;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	@JsonIgnore
	public String getDtoApplyTo() {
		return dtoApplyTo;
	}

	public void setDtoApplyTo(String dtoApplyTo) {
		this.dtoApplyTo = dtoApplyTo;
	}

	@JsonIgnore
	public String getDtoAction() {
		return dtoAction;
	}

	public void setDtoAction(String dtoAction) {
		this.dtoAction = dtoAction;
	}
}
