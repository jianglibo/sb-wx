﻿package com.jianglibo.wx.katharsis.dto;

import java.util.Date;

import io.katharsis.resource.annotations.JsonApiId;

public abstract class DtoBase<T, E> implements Dto<T, E> {

	@JsonApiId
	private Long id;
	
	private Date createdAt;
	
	private String dtoApplyTo;
	
	private String dtoAction;
	
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

	public String getDtoApplyTo() {
		return dtoApplyTo;
	}

	public void setDtoApplyTo(String dtoApplyTo) {
		this.dtoApplyTo = dtoApplyTo;
	}

	public String getDtoAction() {
		return dtoAction;
	}

	public void setDtoAction(String dtoAction) {
		this.dtoAction = dtoAction;
	}

}
