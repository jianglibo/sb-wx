package com.jianglibo.wx.katharsis.dto;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.jianglibo.wx.domain.BaseEntity;

import io.katharsis.resource.annotations.JsonApiId;

public abstract class DtoBase<T, E extends BaseEntity> implements Dto<T, E> {

	@JsonApiId
	private Long id;
	
	private Date createdAt;
	
	private String dtoApplyTo;
	
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

//	@JsonIgnore
	public String getDtoApplyTo() {
		return dtoApplyTo;
	}
	
	public Set<String> calDtoApplyToSet() {
		if (getDtoApplyTo() != null && !getDtoApplyTo().trim().isEmpty()) {
			return Stream.of(getDtoApplyTo().split(",")).map(s -> s.trim()).collect(Collectors.toSet());
		} else {
			return new HashSet<>();
		}
	}

//	@JsonProperty("dtoApplyTo")
	public void setDtoApplyTo(String dtoApplyTo) {
		this.dtoApplyTo = dtoApplyTo;
	}

//	@JsonIgnore
	public String getDtoAction() {
		return dtoAction;
	}

//	@JsonProperty("dtoAction")
	public void setDtoAction(String dtoAction) {
		this.dtoAction = dtoAction;
	}
}
